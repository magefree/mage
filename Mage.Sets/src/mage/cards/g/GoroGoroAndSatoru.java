package mage.cards.g;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerBatchEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DragonSpiritToken;

import java.util.UUID;

public class GoroGoroAndSatoru extends CardImpl {
    public GoroGoroAndSatoru(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.GOBLIN);
        this.addSubType(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        //Whenever one or more creatures you control that entered the battlefield this turn deal combat damage to a
        //player, create a 5/5 red Dragon Spirit creature token with flying.
        this.addAbility(new GoroGoroAndSatoruTriggeredAbility());

        //{1}{R}: Creatures you control gain haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.UntilYourNextEndStep, StaticFilters.FILTER_PERMANENT_CREATURES),
                new ManaCostsImpl<>("{1}{R}")
        ));
    }

    private GoroGoroAndSatoru(final GoroGoroAndSatoru card) {
        super(card);
    }

    @Override
    public GoroGoroAndSatoru copy() {
        return new GoroGoroAndSatoru(this);
    }
}

class GoroGoroAndSatoruTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterPermanent("one or more creatures you control that entered the battlefield this turn");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(EnteredThisTurnPredicate.instance);
    }

    GoroGoroAndSatoruTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new DragonSpiritToken()));
        this.setTriggerPhrase("Whenever one or more creatures you control that entered the battlefield this turn deal combat damage to a player, ");

    }

    private GoroGoroAndSatoruTriggeredAbility(final GoroGoroAndSatoruTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerBatchEvent dEvent = (DamagedPlayerBatchEvent) event;
        for (DamagedEvent damagedEvent : dEvent.getEvents()) {
            if (!damagedEvent.isCombatDamage()) {
                continue;
            }
            Permanent permanent = game.getPermanent(damagedEvent.getSourceId());
            if (!game.getOpponents(getControllerId()).contains(event.getTargetId())) {
                continue;
            }
            if (!filter.match(permanent, getControllerId(), this, game)) {
                continue;
            }
            if (permanent != null && permanent.isControlledBy(getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GoroGoroAndSatoruTriggeredAbility copy() {
        return new GoroGoroAndSatoruTriggeredAbility(this);
    }
}
