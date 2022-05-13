package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author L_J
 */
public final class StaffOfTheLetterMagus extends CardImpl {

    public StaffOfTheLetterMagus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // As Staff of the Letter Magus enters the battlefield, choose a consonant other than N, R, S, or T.
        this.addAbility(new AsEntersBattlefieldAbility(new StaffOfTheLetterMagusChooseLetterEffect()));

        // Whenever a player casts a spell, you gain 1 life for each time the chosen letter appears in that spell’s name.
        this.addAbility(new SpellCastAllTriggeredAbility(new StaffOfTheLetterMagusEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL));
    }

    private StaffOfTheLetterMagus(final StaffOfTheLetterMagus card) {
        super(card);
    }

    @Override
    public StaffOfTheLetterMagus copy() {
        return new StaffOfTheLetterMagus(this);
    }
}

class StaffOfTheLetterMagusChooseLetterEffect extends OneShotEffect {

    public StaffOfTheLetterMagusChooseLetterEffect() {
        super(Outcome.Benefit);
        staticText = "choose a consonant other than N, R, S, or T";
    }

    public StaffOfTheLetterMagusChooseLetterEffect(final StaffOfTheLetterMagusChooseLetterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }

        ChoiceImpl choice = new ChoiceImpl(true);
        choice.setMessage("Choose letter");
        Set<String> choices = new HashSet<>();
        // Can I choose Y?
        // Yes. We play by popular game show rules here. Y is a consonant.
        // https://magic.wizards.com/en/articles/archive/news/unstable-faqawaslfaqpaftidawabiajtbt-2017-12-06
        Character[] forbiddenChars = {'A', 'E', 'I', 'N', 'O', 'R', 'S', 'T', 'U'};
        for (Character letter = 'A'; letter <= 'Z'; letter++) {
            if (Arrays.binarySearch(forbiddenChars, letter) < 0) {
                choices.add(letter.toString());
            }
        }
        choice.setChoices(choices);

        if (controller != null && mageObject != null && controller.choose(outcome, choice, game)) {
            if (!game.isSimulation()) {
                game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
            }
            game.getState().setValue(mageObject.getId() + "_letter", choice.getChoice());
            if (mageObject instanceof Permanent) {
                ((Permanent) mageObject).addInfo("chosen letter", CardUtil.addToolTipMarkTags("Chosen letter: " + choice.getChoice()), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public StaffOfTheLetterMagusChooseLetterEffect copy() {
        return new StaffOfTheLetterMagusChooseLetterEffect(this);
    }
}

class StaffOfTheLetterMagusEffect extends OneShotEffect {

    public StaffOfTheLetterMagusEffect() {
        super(Outcome.GainLife);
        staticText = "you gain 1 life for each time the chosen letter appears in that spell's name";
    }

    public StaffOfTheLetterMagusEffect(final StaffOfTheLetterMagusEffect effect) {
        super(effect);
    }

    @Override
    public StaffOfTheLetterMagusEffect copy() {
        return new StaffOfTheLetterMagusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
        if (controller != null && spell != null) {
            MageObject mageObject = game.getObject(source);
            if (mageObject instanceof Permanent) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
                if (permanent != null && game.getState().getValue(mageObject.getId() + "_letter") != null) {
                    int lifegainValue = 0;
                    String spellName = spell.getName();
                    for (int i = 0; i < spellName.length(); i++) {
                        char letter = spellName.charAt(i);
                        String chosenLetter = (String) game.getState().getValue(mageObject.getId() + "_letter");
                        if (chosenLetter != null && Character.isLetter(letter) && Character.toUpperCase(letter) == chosenLetter.charAt(0)) {
                            lifegainValue++;
                        }
                    }
                    controller.gainLife(lifegainValue, game, source);
                    return true;
                }
            }
        }
        return false;
    }
}
