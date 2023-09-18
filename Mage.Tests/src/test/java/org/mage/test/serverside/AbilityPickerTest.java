package org.mage.test.serverside;

import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentImpl;
import mage.view.AbilityPickerView;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class AbilityPickerTest extends CardTestPlayerBase {

    private GameView prepareGameView() {
        return new GameView(currentGame.getState(), currentGame, playerA.getId(), null);
    }

    @Test
    public void test_PickerChoices_FusedSpells() {
        // must be 3 spells for choices
        Abilities<Ability> abilities = getAbilitiesFromCard("Armed // Dangerous");
        Assert.assertEquals(3, abilities.size());

        AbilityPickerView view = new AbilityPickerView(prepareGameView(), "test name", abilities, "test message");
        Assert.assertEquals(3, view.getChoices().size());
        view.getChoices().values().forEach(c -> {
            Assert.assertTrue("Must start with Cast text, but found: " + c, c.contains("Cast "));
        });
    }

    @Test
    public void test_PickerChoices_AdventureSpells() {
        // must be 2 spells for choices and 1 static ability
        Abilities<Ability> abilities = getAbilitiesFromCard("Foulmire Knight");
        Assert.assertEquals(3, abilities.size());

        AbilityPickerView view = new AbilityPickerView(prepareGameView(), "test name", abilities, "test message");
        Assert.assertEquals(3, view.getChoices().size());
        view.getChoices().values().forEach(c -> {
            if (c.contains("Deathtouch")) {
                return;
            }
            Assert.assertTrue("Must start with Cast text, but found: " + c, c.contains("Cast "));
        });
    }

    @Test
    public void test_PickerChoices_ActivatedAbilities() {
        // must be 1 cast + 3 abilities
        Abilities<Ability> abilities = getAbilitiesFromCard("Dimir Cluestone");
        Assert.assertEquals(4, abilities.size());

        AbilityPickerView view = new AbilityPickerView(prepareGameView(), "test name", abilities, "test message");
        Assert.assertEquals(4, view.getChoices().size());
        int castCount = 0;
        int abilsCount = 0;
        for (String c : view.getChoices().values()) {
            if (c.contains("Cast ")) {
                castCount++;
            } else {
                abilsCount++;
            }
        }
        Assert.assertEquals(1, castCount);
        Assert.assertEquals(3, abilsCount);
    }

    @Test
    public void test_PickerChoices_AdditionalSpells() {
        // must be 2 cast
        Abilities<Ability> abilities = getAbilitiesFromCard("Cling to Dust");
        Assert.assertEquals(2, abilities.size());

        AbilityPickerView view = new AbilityPickerView(prepareGameView(), "test name", abilities, "test message");
        Assert.assertEquals(2, view.getChoices().size());
        view.getChoices().values().forEach(c -> {
            Assert.assertTrue("Must start with Cast text, but found: " + c, c.contains("Cast "));
        });
    }

    private Abilities<Ability> getAbilitiesFromCard(String cardName) {
        CardInfo info = CardRepository.instance.findCard(cardName);
        PermanentImpl permanent = new PermanentCard(info.getCard(), playerA.getId(), currentGame);
        return permanent.getAbilities(currentGame);
    }
}
