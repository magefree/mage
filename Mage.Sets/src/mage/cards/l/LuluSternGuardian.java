package mage.cards.l;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LuluSternGuardian extends CardImpl {

    public LuluSternGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever an opponent attacks you, choose target creature attacking you. Put a stun counter on that creature.
        this.addAbility(new LuluSternGuardianTriggeredAbility());

        // {3}{U}: Proliferate.
        this.addAbility(new SimpleActivatedAbility(new ProliferateEffect(), new ManaCostsImpl<>("{3}{U}")));
    }

    private LuluSternGuardian(final LuluSternGuardian card) {
        super(card);
    }

    @Override
    public LuluSternGuardian copy() {
        return new LuluSternGuardian(this);
    }
}

enum LuluSternGuardianPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getSource().isControlledBy(game.getCombat().getDefenderId(input.getObject().getId()));
    }
}

class LuluSternGuardianTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature attacking you");

    static {
        filter.add(LuluSternGuardianPredicate.instance);
    }

    LuluSternGuardianTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText("choose target creature attacking you. Put a stun counter on that creature"));
        setTriggerPhrase("Whenever an opponent attacks you, ");
        addTarget(new TargetPermanent(filter));
    }

    private LuluSternGuardianTriggeredAbility(final LuluSternGuardianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LuluSternGuardianTriggeredAbility copy() {
        return new LuluSternGuardianTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getTargetId())
                && game.getOpponents(getControllerId()).contains(event.getPlayerId());
    }
}
