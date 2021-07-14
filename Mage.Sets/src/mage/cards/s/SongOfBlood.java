package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class SongOfBlood extends CardImpl {

    public SongOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Put the top four cards of your library into your graveyard.
        // Whenever a creature attacks this turn, it gets +1/+0 until end of turn for each creature card put into your graveyard this way.
        this.getSpellAbility().addEffect(new SongOfBloodEffect());
    }

    private SongOfBlood(final SongOfBlood card) {
        super(card);
    }

    @Override
    public SongOfBlood copy() {
        return new SongOfBlood(this);
    }
}

class SongOfBloodEffect extends OneShotEffect {

    SongOfBloodEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Mill four cards. Whenever a creature attacks this turn, " +
                "it gets +1/+0 until end of turn for each creature card put into your graveyard this way.";

    }

    private SongOfBloodEffect(final SongOfBloodEffect effect) {
        super(effect);
    }

    @Override
    public SongOfBloodEffect copy() {
        return new SongOfBloodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int creatures = controller
                .millCards(4, source, game)
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> game.getState().getZone(card.getId()) == Zone.GRAVEYARD)
                .filter(card1 -> card1.isCreature(game))
                .mapToInt(x -> 1)
                .sum();
        // Setup a delayed trigger to give +X/+0 to any creature attacking this turn..
        DelayedTriggeredAbility delayedAbility = new SongOfBloodTriggeredAbility(creatures);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class SongOfBloodTriggeredAbility extends DelayedTriggeredAbility {

    private final int booster;

    SongOfBloodTriggeredAbility(int booster) {
        super(new BoostTargetEffect(booster, 0, Duration.EndOfTurn), Duration.EndOfTurn, false);
        this.booster = booster;
    }

    private SongOfBloodTriggeredAbility(SongOfBloodTriggeredAbility ability) {
        super(ability);
        this.booster = ability.booster;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent, game));
            }
            return true;
        }
        return false;
    }

    @Override
    public SongOfBloodTriggeredAbility copy() {
        return new SongOfBloodTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks this turn, it gets +" + booster + "/0 until end of turn.";
    }
}
