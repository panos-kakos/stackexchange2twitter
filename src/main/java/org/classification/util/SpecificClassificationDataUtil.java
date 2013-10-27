package org.classification.util;

import static org.classification.util.ClassificationSettings.FEATURES;
import static org.classification.util.ClassificationSettings.PROBES_FOR_CONTENT_ENCODER_VECTOR;
import static org.classification.util.GenericClassificationDataUtil.oneVsAnotherTrainingDataShuffled;
import static org.classification.util.GenericClassificationDataUtil.testData;
import static org.classification.util.GenericClassificationDataUtil.trainingData;
import static org.classification.util.SpecificClassificationUtil.JOB;
import static org.classification.util.SpecificClassificationUtil.NONJOB;
import static org.classification.util.SpecificClassificationUtil.NONPROGRAMMING;
import static org.classification.util.SpecificClassificationUtil.PROGRAMMING;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.mahout.math.NamedVector;

public final class SpecificClassificationDataUtil {

    public static final class Other {
        public static final String SAMPLE = "/classification/sample.classif";
    }

    public static final class TrainingFull {
        public static final String NONJOBS = "/classification/jobs/nonjobs-full.classif";
        public static final String JOBS = "/classification/jobs/jobs-full.classif";

        public static final String NONPROGRAMMING = "/classification/programming/nonprogramming-full.classif";
        public static final String PROGRAMMING = "/classification/programming/programming-full.classif";
    }

    public static final class TrainingCore {
        public static final String NONJOBS = "/classification/jobs/nonjobs-core.classif";
        public static final String JOBS = "/classification/jobs/jobs-core.classif";

        public static final String NONPROGRAMMING = "/classification/programming/nonprogramming-core.classif";
        public static final String PROGRAMMING = "/classification/programming/programming-core.classif";
    }

    public static final class Training {
        public static final String NONJOBS = "/classification/jobs/nonjobs.classif";
        public static final String JOBS = "/classification/jobs/jobs.classif";

        public static final String NONCOMMERCIAL = "/classification/commercial/noncommercial.classif";
        public static final String COMMERCIAL = "/classification/commercial/commercial.classif";

        public static final String NONPROGRAMMING = "/classification/programming/nonprogramming.classif";
        public static final String PROGRAMMING = "/classification/programming/programming.classif";
    }

    public static final class Test {
        public static final String NONJOBS = "/classification/test/jobs/nonjobs.classif";
        public static final String JOBS = "/classification/test/jobs/jobs.classif";

        public static final String NONPROGRAMMING = "/classification/test/programming/nonprogramming.classif";
        public static final String PROGRAMMING = "/classification/test/programming/programming.classif";
    }

    private SpecificClassificationDataUtil() {
        throw new AssertionError();
    }

    // API

    public static final class JobsDataApi {

        // training data - jobs

        public static final List<NamedVector> jobsVsNonJobsTrainingDataDefault() throws IOException {
            return JobsDataApi.jobsVsNonJobsTrainingDataShuffled(PROBES_FOR_CONTENT_ENCODER_VECTOR, FEATURES);
        }

        static final List<NamedVector> jobsVsNonJobsTrainingDataShuffled(final int probes, final int features) throws IOException {
            final List<NamedVector> nonJobsVectors = JobsDataApi.nonJobsTrainingData(probes, features);
            final List<NamedVector> jobsNamedVectors = JobsDataApi.jobsTrainingData(probes, features);
            return oneVsAnotherTrainingDataShuffled(probes, features, nonJobsVectors, jobsNamedVectors);
        }

        public static final List<NamedVector> jobsVsNonJobsCoreTrainingDataShuffled(final int probes, final int features) throws IOException {
            final List<NamedVector> nonJobsVectors = JobsDataApi.nonJobsCoreTrainingData(probes, features);
            final List<NamedVector> jobsNamedVectors = JobsDataApi.jobsCoreTrainingData(probes, features);
            return oneVsAnotherTrainingDataShuffled(probes, features, nonJobsVectors, jobsNamedVectors);
        }

        public static final List<NamedVector> jobsVsNonJobsFullTrainingDataShuffled(final int probes, final int features) throws IOException {
            final List<NamedVector> nonJobsVectors = JobsDataApi.nonJobsFullTrainingData(probes, features);
            final List<NamedVector> jobsNamedVectors = JobsDataApi.jobsFullTrainingData(probes, features);
            return oneVsAnotherTrainingDataShuffled(probes, features, nonJobsVectors, jobsNamedVectors);
        }

        static final List<NamedVector> jobsTrainingData(final int probes, final int features) throws IOException {
            return trainingData(SpecificClassificationDataUtil.Training.JOBS, JOB, probes, features);
        }

        static final List<NamedVector> nonJobsTrainingData(final int probes, final int features) throws IOException {
            return trainingData(SpecificClassificationDataUtil.Training.NONJOBS, NONJOB, probes, features);
        }

        static final List<NamedVector> jobsCoreTrainingData(final int probes, final int features) throws IOException {
            return trainingData(SpecificClassificationDataUtil.TrainingCore.JOBS, JOB, probes, features);
        }

        static final List<NamedVector> nonJobsCoreTrainingData(final int probes, final int features) throws IOException {
            return trainingData(SpecificClassificationDataUtil.TrainingCore.NONJOBS, NONJOB, probes, features);
        }

        static final List<NamedVector> jobsFullTrainingData(final int probes, final int features) throws IOException {
            return trainingData(SpecificClassificationDataUtil.TrainingFull.JOBS, JOB, probes, features);
        }

        static final List<NamedVector> nonJobsFullTrainingData(final int probes, final int features) throws IOException {
            return trainingData(SpecificClassificationDataUtil.TrainingFull.NONJOBS, NONJOB, probes, features);
        }

        // test data - jobs

        static final List<ImmutablePair<String, String>> jobsTestData() throws IOException {
            return testData(SpecificClassificationDataUtil.Test.JOBS, JOB);
        }

        static final List<ImmutablePair<String, String>> nonJobsTestData() throws IOException {
            return testData(SpecificClassificationDataUtil.Test.NONJOBS, NONJOB);
        }

    }

    public static final class ProgrammingDataApi {

        // training data - programming

        public static final List<NamedVector> programmingVsNonProgrammingTrainingDataDefault() throws IOException {
            return ProgrammingDataApi.programmingVsNonProgrammingTrainingDataShuffled(PROBES_FOR_CONTENT_ENCODER_VECTOR, FEATURES);
        }

        static final List<NamedVector> programmingVsNonProgrammingTrainingDataShuffled(final int probes, final int features) throws IOException {
            final List<NamedVector> nonProgrammingVectors = ProgrammingDataApi.nonProgrammingTrainingData(probes, features);
            final List<NamedVector> programmingNamedVectors = ProgrammingDataApi.programmingTrainingData(probes, features);
            return oneVsAnotherTrainingDataShuffled(probes, features, nonProgrammingVectors, programmingNamedVectors);
        }

        static final List<NamedVector> programmingTrainingData(final int probes, final int features) throws IOException {
            return trainingData(SpecificClassificationDataUtil.Training.PROGRAMMING, PROGRAMMING, probes, features);
        }

        static final List<NamedVector> nonProgrammingTrainingData(final int probes, final int features) throws IOException {
            return trainingData(SpecificClassificationDataUtil.Training.NONPROGRAMMING, NONPROGRAMMING, probes, features);
        }

        // test data - programming

        static final List<ImmutablePair<String, String>> programmingTestData() throws IOException {
            return testData(SpecificClassificationDataUtil.Test.PROGRAMMING, PROGRAMMING);
        }

        static final List<ImmutablePair<String, String>> nonProgrammingTestData() throws IOException {
            return testData(SpecificClassificationDataUtil.Test.NONPROGRAMMING, NONPROGRAMMING);
        }

    }

    public static final class CommercialDataApi {

        // training data - commercial

        public static final List<NamedVector> commercialVsNonCommercialTrainingDataDefault() throws IOException {
            return SpecificClassificationDataUtil.JobsDataApi.jobsVsNonJobsTrainingDataShuffled(PROBES_FOR_CONTENT_ENCODER_VECTOR, FEATURES);
        }

        static final List<NamedVector> commercialVsNonCommercialTrainingDataShuffled(final int probes, final int features) throws IOException {
            final List<NamedVector> nonJobsVectors = SpecificClassificationDataUtil.JobsDataApi.nonJobsTrainingData(probes, features);
            final List<NamedVector> jobsNamedVectors = SpecificClassificationDataUtil.JobsDataApi.jobsTrainingData(probes, features);
            return oneVsAnotherTrainingDataShuffled(probes, features, nonJobsVectors, jobsNamedVectors);
        }

        // training data - commercial

        static final List<NamedVector> commercialTrainingData(final int probes, final int features) throws IOException {
            return trainingData(Training.JOBS, JOB, probes, features);
        }

        static final List<NamedVector> nonCommercialTrainingData(final int probes, final int features) throws IOException {
            return trainingData(Training.NONJOBS, NONJOB, probes, features);
        }

    }

}
