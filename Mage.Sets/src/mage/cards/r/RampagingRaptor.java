package mage.cards.r;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ProtectorIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampagingRaptor extends CardImpl {

    public RampagingRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {2}{R}: Rampaging Raptor gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl("{2}{R}")
        ));

        // Whenever Rampaging Raptor deals combat damage to an opponent, it deals that much damage to target planeswalker that player controls or battle that player protects.
        this.addAbility(new RampagingRaptorTriggeredAbility());
    }

    private RampagingRaptor(final RampagingRaptor card) {
        super(card);
    }

    @Override
    public RampagingRaptor copy() {
        return new RampagingRaptor(this);
    }
}

class RampagingRaptorTriggeredAbility extends TriggeredAbilityImpl {

    RampagingRaptorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(SavedDamageValue.MUCH), false);
    }

    private RampagingRaptorTriggeredAbility(final RampagingRaptorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RampagingRaptorTriggeredAbility copy() {
        return new RampagingRaptorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent == null
                || !event.getSourceId().equals(this.getSourceId())
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent(
                "planeswalker " + opponent.getLogName() + " controls " +
                        "or battle " + opponent.getLogName() + " protects"
        );
        filter.add(Predicates.or(
                Predicates.and(
                        CardType.PLANESWALKER.getPredicate(),
                        new ControllerIdPredicate(opponent.getId())
                ),
                Predicates.and(
                        CardType.BATTLE.getPredicate(),
                        new ProtectorIdPredicate(opponent.getId())
                )
        ));
        this.getEffects().setValue("damage", event.getAmount());
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to an opponent, it deals that much damage " +
                "to target planeswalker that player controls or battle that player protects.";
    }
}
