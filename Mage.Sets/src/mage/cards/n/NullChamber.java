package mage.cards.n;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NullChamber extends CardImpl {

    public NullChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.supertype.add(SuperType.WORLD);

        // As Null Chamber enters the battlefield, you and an opponent each name a card other than a basic land card.
        // The named cards can't be played.
        this.addAbility(new AsEntersBattlefieldAbility(new NullChamberChooseEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NullChamberReplacementEffect()));

    }

    private NullChamber(final NullChamber card) {
        super(card);
    }

    @Override
    public NullChamber copy() {
        return new NullChamber(this);
    }
}

class NullChamberChooseEffect extends OneShotEffect {

    public static final String INFO_KEY_CONTROLLER = "CONTROLLER_NAMED_CARD";
    public static final String INFO_KEY_OPPONENT = "OPPONENT_NAMED_CARD";

    public NullChamberChooseEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "you and an opponent each choose a card name other than a basic land card name";
    }

    public NullChamberChooseEffect(final NullChamberChooseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetOpponent chosenOpponent = new TargetOpponent(true);
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getPermanentEntering(source.getSourceId());
        if (sourceObject == null) {
            sourceObject = source.getSourceObject(game);
        }
        if (controller == null || sourceObject == null) {
            return false;
        }
        controller.choose(Outcome.Neutral, chosenOpponent, source, game);
        Player opponent = game.getPlayer(chosenOpponent.getFirstTarget());
        String cardName = ChooseACardNameEffect.TypeOfName.NOT_BASIC_LAND_NAME.getChoice(controller, game, source, false);
        if (cardName != null) {
            game.getState().setValue(source.getSourceId().toString() + INFO_KEY_CONTROLLER, cardName);
            if (sourceObject instanceof Permanent) {
                ((Permanent) sourceObject).addInfo(INFO_KEY_CONTROLLER, CardUtil.addToolTipMarkTags("Named card (Controller): " + cardName), game);
            }
        }
        if (opponent == null) {
            return true;
        }
        cardName = ChooseACardNameEffect.TypeOfName.NOT_BASIC_LAND_NAME.getChoice(opponent, game, source, false);
        if (cardName == null) {
            return true;
        }
        game.getState().setValue(source.getSourceId().toString() + INFO_KEY_OPPONENT, cardName);
        if (sourceObject instanceof Permanent) {
            ((Permanent) sourceObject).addInfo(INFO_KEY_OPPONENT, CardUtil.addToolTipMarkTags("Named card (Opponent): " + cardName), game);
        }
        return true;
    }

    @Override
    public NullChamberChooseEffect copy() {
        return new NullChamberChooseEffect(this);
    }
}

class NullChamberReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    public NullChamberReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.AIDontUseIt);
        staticText = "The named cards can't be played";
    }

    public NullChamberReplacementEffect(final NullChamberReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NullChamberReplacementEffect copy() {
        return new NullChamberReplacementEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast a spell with that name (" + mageObject.getName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        if (object != null) {
            return object.getName().equals(game.getState().getValue(source.getSourceId().toString() + NullChamberChooseEffect.INFO_KEY_CONTROLLER))
                    || object.getName().equals(game.getState().getValue(source.getSourceId().toString() + NullChamberChooseEffect.INFO_KEY_OPPONENT));
        }
        return false;
    }
}
