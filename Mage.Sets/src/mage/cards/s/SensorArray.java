package mage.cards.s;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.RevealHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author NinthWorld
 */
public final class SensorArray extends CardImpl {

    public SensorArray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}");
        

        // When Sensor Array enters the battlefield, look at target opponent's hand, then name a nonland card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RevealHandTargetEffect());
        ability.addEffect(new SensorArrayNameCardEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When that opponent casts a card with the chosen name, sacrifice Sensor Array. If you do, draw two cards.
        ability = new SensorArrayTriggeredAbility();
        ability.addEffect(new DrawCardSourceControllerEffect(2));
        this.addAbility(ability);
    }

    public SensorArray(final SensorArray card) {
        super(card);
    }

    @Override
    public SensorArray copy() {
        return new SensorArray(this);
    }
}

class SensorArrayNameCardEffect extends OneShotEffect {

    public static String INFO_KEY = "NAMED_CARD";
    public static String TARGET_KEY = "NAMED_CARD_TARGET";

    public SensorArrayNameCardEffect() {
        super(Outcome.Benefit);
        staticText = "then name a nonland card";
    }

    public SensorArrayNameCardEffect(final SensorArrayNameCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getPermanentEntering(source.getSourceId());
        if (sourceObject == null) {
            sourceObject = game.getObject(source.getSourceId());
        }
        if (controller != null && sourceObject != null && targetPlayer != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNonLandNames());
            cardChoice.setMessage("Choose a nonland card name");
            cardChoice.clearChoice();
            if (controller.choose(Outcome.Detriment, cardChoice, game)) {
                String cardName = cardChoice.getChoice();
                if (!game.isSimulation()) {
                    game.informPlayers(sourceObject.getLogName() + ", named card: [" + cardName + ']');
                }
                game.getState().setValue(source.getSourceId().toString() + INFO_KEY, cardName);
                game.getState().setValue(source.getSourceId().toString() + TARGET_KEY, targetPlayer.getId());
                if (sourceObject instanceof Permanent) {
                    ((Permanent) sourceObject).addInfo(INFO_KEY, CardUtil.addToolTipMarkTags("Named card: " + cardName), game);
                    ((Permanent) sourceObject).addInfo(TARGET_KEY, CardUtil.addToolTipMarkTags("Target player: " + targetPlayer.getName()), game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public SensorArrayNameCardEffect copy() {
        return new SensorArrayNameCardEffect(this);
    }
}

class SensorArrayTriggeredAbility extends TriggeredAbilityImpl {

    public SensorArrayTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public SensorArrayTriggeredAbility(final SensorArrayTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public SensorArrayTriggeredAbility copy() {
        return new SensorArrayTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        if (object != null) {
            if (game.getState().getPlayersInRange(this.controllerId, game).contains(event.getPlayerId()) // controller in range
                    && object.getName().equals(game.getState().getValue(this.sourceId.toString() + SensorArrayNameCardEffect.INFO_KEY))
                    && event.getPlayerId().equals(game.getState().getValue(this.sourceId.toString() + SensorArrayNameCardEffect.TARGET_KEY))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When that opponent casts a card with the chosen name, sacrifice {this}";
    }
}