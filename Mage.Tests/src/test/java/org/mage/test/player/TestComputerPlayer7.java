package org.mage.test.player;

import mage.MageObject;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.player.ai.ComputerPlayer7;
import mage.target.Target;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * @author JayDi85
 */

// mock class to override AI logic in tests
public class TestComputerPlayer7 extends ComputerPlayer7 {

    private TestPlayer testPlayerLink;

    public TestComputerPlayer7(String name, RangeOfInfluence range, int skill) {
        super(name, range, skill);
    }

    public void setTestPlayerLink(TestPlayer testPlayerLink) {
        this.testPlayerLink = testPlayerLink;
    }

    @Override
    public SpellAbility chooseSpellAbilityForCast(SpellAbility ability, Game game, boolean noMana) {
        // copy-paste for TestComputerXXX

        // workaround to cast fused cards in tests by it's NAMES (Wear, Tear, Wear // Tear)
        // reason: TestPlayer uses outer computerPlayer to cast, not TestPlayer
        switch (ability.getSpellAbilityType()) {
            case SPLIT:
            case SPLIT_FUSED:
            case SPLIT_AFTERMATH:
                if (!this.testPlayerLink.getChoices().isEmpty()) {
                    MageObject object = game.getObject(ability.getSourceId());
                    if (object != null) {
                        LinkedHashMap<UUID, ActivatedAbility> useableAbilities = this.getSpellAbilities(object, game.getState().getZone(object.getId()), game);

                        // left, right or fused cast
                        for (String choose : this.testPlayerLink.getChoices()) {
                            for (ActivatedAbility activatedAbility : useableAbilities.values()) {
                                if (activatedAbility instanceof SpellAbility) {
                                    if (((SpellAbility) activatedAbility).getCardName().equals(choose)) {
                                        return (SpellAbility) activatedAbility;
                                    }
                                }
                            }
                        }
                    }
                }
        }

        // default implementation by AI
        return super.chooseSpellAbilityForCast(ability, game, noMana);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
        // copy-paste for TestComputerXXX

        // workaround for discard spells
        // reason: TestPlayer uses outer computerPlayer to discard but inner code uses choose
        return testPlayerLink.choose(outcome, target, sourceId, game);
    }
}
