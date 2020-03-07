package ca.ulaval.glo2003.beds.services;

import ca.ulaval.glo2003.beds.domain.*;
import ca.ulaval.glo2003.beds.rest.BedRequest;
import ca.ulaval.glo2003.beds.rest.BedResponse;
import ca.ulaval.glo2003.beds.rest.mappers.BedMapper;
import ca.ulaval.glo2003.beds.rest.mappers.BedMatcherMapper;
import ca.ulaval.glo2003.beds.rest.mappers.BedNumberMapper;
import java.util.*;
import java.util.stream.Collectors;

public class BedService {

  private final BedFactory bedFactory;
  private final BedMapper bedMapper;
  private final BedNumberMapper bedNumberMapper;
  private final BedMatcherMapper bedMatcherMapper;
  private final BedRepository bedRepository;
  private final BedStarsCalculator bedStarsCalculator;

  public BedService(
      BedFactory bedFactory,
      BedMapper bedMapper,
      BedNumberMapper bedNumberMapper,
      BedMatcherMapper bedMatcherMapper,
      BedRepository bedRepository,
      BedStarsCalculator bedStarsCalculator) {
    this.bedFactory = bedFactory;
    this.bedMapper = bedMapper;
    this.bedNumberMapper = bedNumberMapper;
    this.bedMatcherMapper = bedMatcherMapper;
    this.bedRepository = bedRepository;
    this.bedStarsCalculator = bedStarsCalculator;
  }

  public String add(BedRequest request) {
    Bed bed = bedMapper.fromRequest(request);

    bed = bedFactory.create(bed);

    bedRepository.add(bed);

    return bed.getNumber().toString();
  }

  public List<BedResponse> getAll(Map<String, String[]> params) {
    BedMatcher bedMatcher = bedMatcherMapper.fromRequestParams(params);

    List<Bed> beds = bedRepository.getAll();

    List<Bed> matchingBeds = beds.stream().filter(bedMatcher::matches).collect(Collectors.toList());

    return matchingBeds.stream()
        .map(
            matchingBed ->
                bedMapper.toResponseWithNumber(
                    matchingBed, bedStarsCalculator.calculateStars(matchingBed)))
        .sorted(Comparator.comparingInt(BedResponse::getStars).reversed())
        .collect(Collectors.toList());
  }

  public BedResponse getByNumber(String number) {
    UUID bedNumber = bedNumberMapper.fromString(number);

    Bed bed = bedRepository.getByNumber(bedNumber);

    return bedMapper.toResponseWithoutNumber(bed, bedStarsCalculator.calculateStars(bed));
  }
}
