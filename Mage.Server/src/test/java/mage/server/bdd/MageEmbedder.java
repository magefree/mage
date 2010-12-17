package mage.server.bdd;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromRelativeFile;
import org.jbehave.core.io.StoryResourceNotFound;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains configuration for working with steps and stories in Mage.
 *
 * @author nantuko
 */
public class MageEmbedder extends Embedder {

    /**
     * We meed to extend LoadFromRelativeFile because of bug in replacing slashes
     *
     * @author nantuko
     */
    private class MageStoryFilePath extends LoadFromRelativeFile {

        private final StoryFilePath[] traversals;
        private final URL location;

        public MageStoryFilePath(URL location, StoryFilePath... traversals) {
            super(location, traversals);
            this.location = location;
            this.traversals = traversals;
        }

        @Override
        public String loadResourceAsText(String resourcePath) {
            List<String> traversalPaths = new ArrayList<String>();
            String locationPath = new File(location.getFile()).getAbsolutePath();

            String filePath = locationPath.replace("target\\test-classes", "src/test/java") + "/" + resourcePath;
            File file = new File(filePath);
            if (file.exists()) {
                    return loadContent(filePath);
            }

            throw new StoryResourceNotFound(resourcePath, traversalPaths);
        }

    }

    @Override
    public Configuration configuration() {
        Class<?> embedderClass = this.getClass();
        URL codeLocation = CodeLocations.codeLocationFromClass(embedderClass);
        Configuration configuration = new MostUsefulConfiguration()
             .useStoryLoader(new MageStoryFilePath(codeLocation, LoadFromRelativeFile.mavenModuleTestStoryFilePath("src/test/java") ))
             .useStoryReporterBuilder(new StoryReporterBuilder()
                     .withCodeLocation(codeLocation)
                     .withDefaultFormats());
        return configuration;
    }
 
    @Override
    public List<CandidateSteps> candidateSteps() {
        return new InstanceStepsFactory(configuration(), new MageSteps()).createCandidateSteps();
    }
}