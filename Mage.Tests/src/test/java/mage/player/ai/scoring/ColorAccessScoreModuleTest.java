package mage.player.ai.scoring;

import org.junit.Assert;
import org.junit.Test;

public class ColorAccessScoreModuleTest {

    @Test
    public void usesCommanderIdentityForAnyColorCommanderMana() {
        Assert.assertEquals(
                "UR",
                ColorAccessScoreModule.describeProducedColorsForTest(
                        "{T}: Add one mana of any color in your commander's color identity.",
                        "UR"
                )
        );
    }

    @Test
    public void readsExplicitManaSymbolsFromManaRules() {
        Assert.assertEquals(
                "UG",
                ColorAccessScoreModule.describeProducedColorsForTest(
                        "{T}: Add {G} or {U}.",
                        ""
                )
        );
    }

    @Test
    public void treatsGenericAnyColorAsAllColorsWithoutCommanderContext() {
        Assert.assertEquals(
                "WUBRG",
                ColorAccessScoreModule.describeProducedColorsForTest(
                        "{T}: Add one mana of any color.",
                        ""
                )
        );
    }
}
