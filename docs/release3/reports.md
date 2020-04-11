# Reports

This document serves as a draft for new classes in `reports` package. It is used for developing release 3. See [flows](flows.md) for more information.

```
/reports

  /domain

    /dimensions

      ReportDimension
        name : ReportDimensions (enum)
        split(List<ReportPeriodData>) : List<ReportPeriodData> (abstract, must also add dim  ension each PeriodReportData)

      LodgingModeDimension : ReportDimension
        split(List<ReportPeriodData>) : List<ReportPeriodData>

      PackageDimension : ReportDimension
        split(List<ReportPeriodData>) : List<ReportPeriodData>

    /metrics

      ReportMetric
        name : ReportMetrics (enum)
        calculate(ReportPeriodData) : ReportPeriodData (abstract, must also add metrict to Per  iodReportData)

      IncomeMetric
        value : Price
        calculate(ReportPeriodData) : ReportPeriodData

      ReservationMetric
        value : int
        calculate(ReportPeriodData) : ReportPeriodData

      CancelationMetric
        value : int
        calculate(ReportPeriodData) : ReportPeriodData

    /assemblers

      ReportQueryParamAssembler
        assemble(BedQueryBuilder builder, Map<String, List<String>> params) : ReportQueryBuilder

      ScopeQueryParamAssembler : ReportQueryParamAssembler
        assemble(...) : ReportQueryBuilder (uses builder.withPeriods(...))

      MetricQueryParamAssembler : ReportQueryParamAssembler
        assemble(...) : ReportQueryBuilder (uses builder.withMetrics(...))

      DimensionQueryParamAssembler : ReportQueryParamAssembler
        assemble(...) : ReportQueryBuilder (uses builder.withDimensions(...))

    ReportQuery
      periods : List<ReportPeriod>
      dimensions : List<ReportDimension>
      metrics : List<ReportMetric>
      transactions : List<Transaction>
      setTransactions(List<Transaction>)
      execute() : List<ReportPeriod>

    ReportQueryBuilder
      aReportQuery() : ReportQueryBuilder
      withPeriods(List<ReportPeriod>) : ReportQueryBuilder
      withDimensions(List<ReportDimension>) : ReportQueryBuilder
      withMetrics(List<ReportMetric>) : ReportQueryBuilder
      withTransactions(List<Transaction>) : ReportQueryBuilder
      build() : ReportQuery

    ReportQueryFactory
      create(Map<String>, List<String>) : ReportQuery

    ReportPeriod
      name : ReportPeriods (enum)
      bookingPeriod : BookingPeriod

    ReportPeriodData
      dimensions : List<ReportDimension> (default = empty)
      metrics : List<ReportMetric> (default = empty)
      transactions : List<Transaction> (default = empty)

    ReportScopes (enum)

    ReportDimensions (enum)

    ReportMetrics (enum)

  /services

    ReportService
      getAll(Map<String, List<String>>) : List<ReportPeriodResponse>

  /handlers

    ReportException : RuntimeException

    InvalidReportScopeException : ReportException

    InvalidReportMetricsException : ReportException

    InvalidReportDimensionsException : ReportException

  /converters

    ReportConverter
      toResponses(List<ReportPeriod>) : List<ReportPeriodResponse>
      toResponse(ReportPeriod) : ReportPeriodResponse

  /infrastructure

    /dimensions

      InMemoryLodgingModeDimension : LodgingModeDimension
        split(List<ReportPeriodData>) : List<ReportPeriodData>

      InMemoryPackageDimension : PackageDimension
        split(List<ReportPeriodData>) : List<ReportPeriodData>

    InMemoryReportQueryBuilder : ReportQueryBuilder
      dimensions : List<ReportDimension> (default = empty)
      
    InMemoryReportQuery : ReportQuery

  /rest

    /handlers

      ReportErrorFactory : AbstractErrorFactory<ReportException>

      InvalidReportScopeErrorFactory : ReportErrorFactory

      InvalidReportMetricsErrorFactory : ReportErrorFactory

      InvalidReportDimensionsErrorFactory : ReportErrorFactory

    /handlers
    
      ReportExceptionHandler : AbstractExceptionHandler<ReportException>
        factories : Set<ErrorFactory<ReportException>>
        handle(BedException, Request, Response)

    ReportResource
      getAll(Request, Response)

    ReportPeriodResponse
      period : String
      data : List<ReportPeriodDataResponse>

    ReportPeriodDataResponse
      dimensions : List<ReportDimensionResponse>
      metrics : List<ReportMetricResponse>

    ReportDimensionResponse
      name : String
      value : String

    ReportMetricResponse
      name : String

    ReportIntegerMetricResponse : ReportMetricResponse
      value : int

    ReportDoubleMetricResponse : ReportMetricResponse
      value : double
```