package org.mage.test.serverside;

import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentImpl;
import mage.view.AbilityPickerView;
import mage.view.GameView;
import mage.view.SimpleCardView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        PermanentImpl permanent = new PermanentCard(info.createCard(), playerA.getId(), currentGame);
        return permanent.getAbilities(currentGame);
    }

    @Test
    public void test_RealGame_ActivatedAbilities_All() {
        // possible bug: wrongly enabled ability, see #10642

        // Activated abilities of lands your opponents control can't be activated unless they're mana abilities.
        addCard(Zone.BATTLEFIELD, playerA, "Sharkey, Tyrant of the Shire", 1);
        // {T}: Add {W}.
        // {T}: Return target legendary creature to its owner's hand.
        addCard(Zone.BATTLEFIELD, playerA, "Karakas", 1);

        // have all abilities
        runCode("all available", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            List<String> needList = new ArrayList<>(Arrays.asList(
                    "{T}: Add {W}.",
                    "{T}: Return target legendary creature to its owner's hand."
            ));
            Collections.sort(needList);
            assertPlayableAbilities(needList);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_RealGame_ActivatedAbilities_Restricted() {
        // possible bug: wrongly enabled ability, see #10642

        // Activated abilities of lands your opponents control can't be activated unless they're mana abilities.
        addCard(Zone.BATTLEFIELD, playerB, "Sharkey, Tyrant of the Shire", 1);
        // {T}: Add {W}.
        // {T}: Return target legendary creature to its owner's hand.
        addCard(Zone.BATTLEFIELD, playerA, "Karakas", 1);

        // have mana abilities only
        runCode("non-mana ability disabled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            List<String> needList = new ArrayList<>(Collections.singletonList(
                    "{T}: Add {W}."
            ));
            Collections.sort(needList);
            assertPlayableAbilities(needList);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    private void assertPlayableAbilities(List<String> need) {
        // server side
        List<String> realList = playerA.getPlayable(currentGame, true).stream()
                .map(Ability::getRule)
                .sorted()
                .collect(Collectors.toList());
        Assert.assertEquals("wrong server side playable list", need.toString(), realList.toString());

        // client side as game data
        GameView gameView = getGameView(playerA);
        realList.clear();
        gameView.getCanPlayObjects().getObjects().forEach((objectId, stats) -> {
            stats.getPlayableAbilityIds().forEach(abilityId -> {
                Ability ability = currentGame.getAbility(abilityId, objectId).orElse(null);
                realList.add(ability == null ? "null" : ability.getRule());
            });
        });
        Collections.sort(realList);
        Assert.assertEquals("wrong client side playable list", need.toString(), realList.toString());
    }
}
