package mage.cards.l;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LagrellaTheMagpie extends CardImpl {

    public LagrellaTheMagpie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Lagrella, the Magpie enters the battlefield, exile any number of other target creatures controlled by different players until Lagrella leaves the battlefield. When an exiled card enters the battlefield under your control this way, put two +1/+1 counters on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LagrellaTheMagpieEffect(), false);
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        ability.addTarget(new LagrellaTheMagpieTarget());
        this.addAbility(ability);
    }

    private LagrellaTheMagpie(final LagrellaTheMagpie card) {
        super(card);
    }

    @Override
    public LagrellaTheMagpie copy() {
        return new LagrellaTheMagpie(this);
    }
}

class LagrellaTheMagpieEffect extends OneShotEffect {

    LagrellaTheMagpieEffect() {
        super(Outcome.Benefit);
        staticText = "exile any number of other target creatures controlled by different players " +
                "until {this} leaves the battlefield. When an exiled card enters the battlefield " +
                "under your control this way, put two +1/+1 counters on it";
    }

    private LagrellaTheMagpieEffect(final LagrellaTheMagpieEffect effect) {
        super(effect);
    }

    @Override
    public LagrellaTheMagpieEffect copy() {
        return new LagrellaTheMagpieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        this.getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .forEach(cards::add);
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        game.addDelayedTriggeredAbility(new LagrellaTheMagpieTriggeredAbility(cards, game), source);
        return true;
    }
}

class LagrellaTheMagpieTarget extends TargetPermanent {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("other creatures controlled by different players");

    static {
        filter.add(AnotherPredicate.instance);
    }

    LagrellaTheMagpieTarget() {
        super(0, Integer.MAX_VALUE, filter, false);
    }

    private LagrellaTheMagpieTarget(final LagrellaTheMagpieTarget target) {
        super(target);
    }

    @Override
    public LagrellaTheMagpieTarget copy() {
        return new LagrellaTheMagpieTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Permanent creature = game.getPermanent(id);
        if (creature == null) {
            return false;
        }
        return this.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .noneMatch(permanent -> !creature.getId().equals(permanent.getId())
                        && creature.isControlledBy(permanent.getControllerId())
                );
    }
}

class LagrellaTheMagpieTriggeredAbility extends DelayedTriggeredAbility {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    LagrellaTheMagpieTriggeredAbility(Cards cards, Game game) {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), Duration.Custom, false, false);
        cards.getCards(game)
                .stream()
                .map(card -> new MageObjectReference(card, game, 1))
                .forEach(morSet::add);
    }

    private LagrellaTheMagpieTriggeredAbility(final LagrellaTheMagpieTriggeredAbility ability) {
        super(ability);
        this.morSet.addAll(ability.morSet);
    }

    @Override
    public LagrellaTheMagpieTriggeredAbility copy() {
        return new LagrellaTheMagpieTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent.isControlledBy(this.getControllerId())
                && morSet.stream().anyMatch(mor -> mor.refersTo(permanent, game))) {
            this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When an exiled card enters the battlefield under your control this way, put two +1/+1 counters on it.";
    }
}
