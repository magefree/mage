package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 * @author TheElk801
 */
public final class MonumentToPerfection extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("a basic, Sphere, or Locus land card");

    static {
        filter.add(Predicates.or(
                SuperType.BASIC.getPredicate(),
                SubType.SPHERE.getPredicate(),
                SubType.LOCUS.getPredicate()
        ));
    }

    public MonumentToPerfection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {3}, {T}: Search your library for a basic, Sphere, or Locus land card, reveal it, put it into your hand, then shuffle.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true
        ), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {3}: Monument to Perfection becomes a 9/9 Phyrexian Construct artifact creature, loses all abilities, and gains indestructible and toxic 9. Activate only if there are nine or more lands with different names among the basic, Sphere, and Locus lands you control.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new CreatureToken(
                        9, 9, "9/9 Phyrexian Construct artifact creature, " +
                        "loses all abilities, and gains indestructible and toxic 9", SubType.PHYREXIAN, SubType.CONSTRUCT
                ).withType(CardType.ARTIFACT)
                        .withAbility(IndestructibleAbility.getInstance())
                        .withAbility(new ToxicAbility(9)),
                        null, Duration.Custom, true,
                        false, null, null, true
                ), new GenericManaCost(3), MonumentToPerfectionCondition.instance
        ).addHint(MonumentToPerfectionValue.getHint()));
    }

    private MonumentToPerfection(final MonumentToPerfection card) {
        super(card);
    }

    @Override
    public MonumentToPerfection copy() {
        return new MonumentToPerfection(this);
    }
}

enum MonumentToPerfectionValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(Predicates.or(
                SuperType.BASIC.getPredicate(),
                SubType.SPHERE.getPredicate(),
                SubType.LOCUS.getPredicate()
        ));
    }

    private static final Hint hint = new ValueHint(
            "Different names among basics, Spheres, and Locuses you control", instance
    );

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), game)
                .stream()
                .map(permanent -> permanent.getName())
                .filter(s -> s.length() > 0)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public MonumentToPerfectionValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    public static Hint getHint() {
        return hint;
    }
}

enum MonumentToPerfectionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return MonumentToPerfectionValue.instance.calculate(game, source, null) >= 9;
    }

    @Override
    public String toString() {
        return "there are nine or more lands with different names among the basic, Sphere, and Locus lands you control";
    }
}