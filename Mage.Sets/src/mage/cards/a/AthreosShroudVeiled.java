package mage.cards.a;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AthreosShroudVeiled extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AthreosShroudVeiled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to white and black is less than seven, Athreos isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.WB, 7))
                .addHint(DevotionCount.WB.getHint()));

        // At the beginning of your end step, put a coin counter on another target creature.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.COIN.createInstance()),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Whenever a creature with a coin counter on it dies or is put into exile, 
        // return that card to the battlefield under your control.
        this.addAbility(new AthreosShroudVeiledTriggeredAbility());
    }

    private AthreosShroudVeiled(final AthreosShroudVeiled card) {
        super(card);
    }

    @Override
    public AthreosShroudVeiled copy() {
        return new AthreosShroudVeiled(this);
    }
}

class AthreosShroudVeiledTriggeredAbility extends TriggeredAbilityImpl {

    AthreosShroudVeiledTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private AthreosShroudVeiledTriggeredAbility(final AthreosShroudVeiledTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getFromZone() == Zone.BATTLEFIELD
                && (zEvent.getToZone() == Zone.GRAVEYARD
                || zEvent.getToZone() == Zone.EXILED);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();
        if (permanent == null
                || !permanent.isCreature(game)
                || !permanent.getCounters(game).containsKey(CounterType.COIN)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new AthreosShroudVeiledEffect(new MageObjectReference(zEvent.getTarget(), game)));
        return true;
    }

    @Override
    public AthreosShroudVeiledTriggeredAbility copy() {
        return new AthreosShroudVeiledTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature with a coin counter on it dies or is put into exile, "
                + "return that card to the battlefield under your control.";
    }
}

class AthreosShroudVeiledEffect extends OneShotEffect {

    private final MageObjectReference mor;

    AthreosShroudVeiledEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    private AthreosShroudVeiledEffect(final AthreosShroudVeiledEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public AthreosShroudVeiledEffect copy() {
        return new AthreosShroudVeiledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = game.getCard(mor.getSourceId());
        return card != null
                && card.getZoneChangeCounter(game) - 1 == mor.getZoneChangeCounter()
                && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
