package mage.cards.n;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class NullChamber extends CardImpl {

    public NullChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.addSuperType(SuperType.WORLD);

        // As Null Chamber enters the battlefield, you and an opponent each name a card other than a basic land card.
        // The named cards can't be played.
        this.addAbility(new AsEntersBattlefieldAbility(new NullChamberChooseEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NullChamberReplacementEffect()));

    }

    public NullChamber(final NullChamber card) {
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
        staticText = "you and an opponent each name a card other than a basic land card";
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
            sourceObject = game.getObject(source.getSourceId());
        }
        if (controller != null
                && sourceObject != null
                && controller.choose(Outcome.Neutral, chosenOpponent, source.getSourceId(), game)) {
            Player opponent = game.getPlayer(chosenOpponent.getFirstTarget());
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNotBasicLandNames());
            cardChoice.setMessage("Choose a card name other than a basic land card name");
            cardChoice.clearChoice();
            if (controller.choose(Outcome.Detriment, cardChoice, game)) {
                String cardName = cardChoice.getChoice();
                if (!game.isSimulation()) {
                    game.informPlayers(sourceObject.getLogName() + ", controller named card: [" + cardName + ']');
                }
                game.getState().setValue(source.getSourceId().toString() + INFO_KEY_CONTROLLER, cardName);
                if (sourceObject instanceof Permanent) {
                    ((Permanent) sourceObject).addInfo(INFO_KEY_CONTROLLER, CardUtil.addToolTipMarkTags("Named card (Controller): " + cardName), game);
                }
            }
            cardChoice.clearChoice();
            if (opponent != null
                    && opponent.choose(Outcome.Detriment, cardChoice, game)) {
                String cardName = cardChoice.getChoice();
                if (!game.isSimulation()) {
                    game.informPlayers(sourceObject.getLogName() + ",chosen opponent named card: [" + cardName + ']');
                }
                game.getState().setValue(source.getSourceId().toString() + INFO_KEY_OPPONENT, cardName);
                if (sourceObject instanceof Permanent) {
                    ((Permanent) sourceObject).addInfo(INFO_KEY_OPPONENT, CardUtil.addToolTipMarkTags("Named card (Opponent): " + cardName), game);
                }
                return true;
            }
        }
        return false;
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
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't cast a spell with that name (" + mageObject.getLogName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.CAST_SPELL;
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
