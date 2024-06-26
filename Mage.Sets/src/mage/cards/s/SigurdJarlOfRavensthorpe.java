package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BoastAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SigurdJarlOfRavensthorpe extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SAGA);

    public SigurdJarlOfRavensthorpe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Boast -- {1}: Put a lore counter on target Saga you control or remove one from it.
        Ability ability = new BoastAbility(new SigurdJarlOfRavensthorpeEffect(), "{1}");
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Whenever you put a lore counter on a Saga you control, put a +1/+1 counter on up to one other target creature.
        this.addAbility(new SigurdJarlOfRavensthorpeTriggeredAbility());
    }

    private SigurdJarlOfRavensthorpe(final SigurdJarlOfRavensthorpe card) {
        super(card);
    }

    @Override
    public SigurdJarlOfRavensthorpe copy() {
        return new SigurdJarlOfRavensthorpe(this);
    }
}

class SigurdJarlOfRavensthorpeEffect extends OneShotEffect {

    SigurdJarlOfRavensthorpeEffect() {
        super(Outcome.Benefit);
        staticText = "put a lore counter on target Saga you control or remove one from it";
    }

    private SigurdJarlOfRavensthorpeEffect(final SigurdJarlOfRavensthorpeEffect effect) {
        super(effect);
    }

    @Override
    public SigurdJarlOfRavensthorpeEffect copy() {
        return new SigurdJarlOfRavensthorpeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        if (player.chooseUse(
                outcome, "Add a lore counter to " + permanent.getIdName() + " or remove one?",
                null, "Add", "Remove", source, game
        )) {
            permanent.addCounters(CounterType.LORE.createInstance(), source, game);
        } else {
            permanent.removeCounters(CounterType.LORE.createInstance(), source, game);
        }
        return true;
    }
}

class SigurdJarlOfRavensthorpeTriggeredAbility extends TriggeredAbilityImpl {

    SigurdJarlOfRavensthorpeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.setTriggerPhrase("Whenever you put a lore counter on a Saga you control, ");
    }

    private SigurdJarlOfRavensthorpeTriggeredAbility(final SigurdJarlOfRavensthorpeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SigurdJarlOfRavensthorpeTriggeredAbility copy() {
        return new SigurdJarlOfRavensthorpeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && permanent.hasSubtype(SubType.SAGA, game)
                && permanent.isControlledBy(getControllerId())
                && CounterType.P1P1.getName().equals(event.getData())
                && isControlledBy(event.getPlayerId());
    }
}
