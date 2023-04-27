package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author MarcoMarin / HCrescent
 */
public final class Shapeshifter extends CardImpl {

    public Shapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(7);

        // As Shapeshifter enters the battlefield, choose a number between 0 and 7.
        this.addAbility(new AsEntersBattlefieldAbility(new ShapeshifterEffect()));
        // At the beginning of your upkeep, you may choose a number between 0 and 7.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ShapeshifterEffect(), TargetController.YOU, true));
        // Shapeshifter's power is equal to the last chosen number and its toughness is equal to 7 minus that number.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ShapeshifterContinuousEffect()));
    }

    private Shapeshifter(final Shapeshifter card) {
        super(card);
    }

    @Override
    public Shapeshifter copy() {
        return new Shapeshifter(this);
    }
}

class ShapeshifterEffect extends OneShotEffect {

    public ShapeshifterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose a number between 0 and 7.";
    }

    public ShapeshifterEffect(final ShapeshifterEffect effect) {
        super(effect);
    }

    @Override
    public ShapeshifterEffect copy() {
        return new ShapeshifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getPermanent(source.getSourceId());
        }
        if (controller != null) {
            Choice numberChoice = new ChoiceImpl();
            numberChoice.setMessage("Choose a number beween 0 and 7");
            Set<String> numbers = new HashSet<>();
            for (int i = 0; i <= 7; i++) {
                numbers.add(Integer.toString(i));
            }
            numberChoice.setChoices(numbers);
            if (!controller.choose(Outcome.Neutral, numberChoice, game)) {
                return false;
            }
            game.informPlayers("Shapeshifter, chosen number: [" + numberChoice.getChoice() + ']');
            game.getState().setValue(source.getSourceId().toString() + "_Shapeshifter", numberChoice.getChoice());
            if (mageObject instanceof Permanent) {
                ((Permanent) mageObject).addInfo("lastChosenNumber", CardUtil.addToolTipMarkTags("Last chosen number: " + numberChoice.getChoice()), game);
            }
        }
        return false;
    }
}

class ShapeshifterContinuousEffect extends ContinuousEffectImpl {

    public ShapeshifterContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, Outcome.BoostCreature);
        staticText = "{this}'s power is equal to the last chosen number and its toughness is equal to 7 minus that number.";
    }

    public ShapeshifterContinuousEffect(final ShapeshifterContinuousEffect effect) {
        super(effect);
    }

    @Override
    public ShapeshifterContinuousEffect copy() {
        return new ShapeshifterContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        String lastChosen = (String) game.getState().getValue(source.getSourceId().toString() + "_Shapeshifter");
        if (permanent != null && lastChosen != null) {
            int lastChosenNumber = Integer.parseInt(lastChosen);
            permanent.getPower().setModifiedBaseValue(lastChosenNumber);
            permanent.getToughness().setModifiedBaseValue(7 - lastChosenNumber);
            return true;
        }
        return false;
    }
}
