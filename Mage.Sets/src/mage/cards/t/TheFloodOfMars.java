package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFloodOfMars extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target creature or land");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public TheFloodOfMars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // Water Always Wins -- Whenever The Flood of Mars attacks, put a flood counter on another target creature or land. If it's a creature, it becomes a copy of The Flood of Mars. If it's a land, it becomes an Island in addition to its other types.
        Ability ability = new AttacksTriggeredAbility(new TheFloodOfMarsEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.withFlavorWord("Water Always Wins"));
    }

    private TheFloodOfMars(final TheFloodOfMars card) {
        super(card);
    }

    @Override
    public TheFloodOfMars copy() {
        return new TheFloodOfMars(this);
    }
}

class TheFloodOfMarsEffect extends OneShotEffect {

    TheFloodOfMarsEffect() {
        super(Outcome.Benefit);
        staticText = "put a flood counter on another target creature or land. If it's a creature, " +
                "it becomes a copy of {this}. If it's a land, it becomes an Island in addition to its other types";
    }

    private TheFloodOfMarsEffect(final TheFloodOfMarsEffect effect) {
        super(effect);
    }

    @Override
    public TheFloodOfMarsEffect copy() {
        return new TheFloodOfMarsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.FLOOD.createInstance(), source, game);
        boolean isCreature = permanent.isCreature(game);
        boolean isLand = permanent.isLand(game);
        if (isCreature) {
            Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
            if (sourcePermanent != null) {
                game.copyPermanent(Duration.Custom, sourcePermanent, permanent.getId(), source, new EmptyCopyApplier());
            }
        }
        if (isLand) {
            game.addEffect(new AddCardSubTypeTargetEffect(SubType.ISLAND, Duration.Custom)
                    .setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
