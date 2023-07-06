package mage.cards.l;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LandrovalHorizonWitness extends CardImpl {

    public LandrovalHorizonWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever two or more creatures you control attack a player, target attacking creature without flying gains flying until end of turn.
        this.addAbility(new LandrovalHorizonWitnessTriggeredAbility());
    }

    private LandrovalHorizonWitness(final LandrovalHorizonWitness card) {
        super(card);
    }

    @Override
    public LandrovalHorizonWitness copy() {
        return new LandrovalHorizonWitness(this);
    }
}

class LandrovalHorizonWitnessTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter
            = new FilterAttackingCreature("attacking creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    LandrovalHorizonWitnessTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.addTarget(new TargetPermanent(filter));
        setTriggerPhrase("Whenever two or more creatures you control attack a player, ");
    }

    private LandrovalHorizonWitnessTriggeredAbility(final LandrovalHorizonWitnessTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LandrovalHorizonWitnessTriggeredAbility copy() {
        return new LandrovalHorizonWitnessTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId())
                && game.getPlayer(event.getTargetId()) != null
                && ((DefenderAttackedEvent) event).getAttackers(game).size() >= 2;
    }
}
