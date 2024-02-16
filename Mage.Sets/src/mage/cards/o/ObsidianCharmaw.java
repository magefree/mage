package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObsidianCharmaw extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("nonbasic land an opponent controls");

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final Hint hint = new ValueHint(
            "Lands your opponents control that could produce {C}", ObsidianCharmawValue.instance
    );

    public ObsidianCharmaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This spell costs {1} less to cast for each land your opponents control that could produce {C}.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, ObsidianCharmawValue.instance)
        ).addHint(hint));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Obsidian Charmaw enters the battlefield, destroy target nonbasic land an opponent controls
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ObsidianCharmaw(final ObsidianCharmaw card) {
        super(card);
    }

    @Override
    public ObsidianCharmaw copy() {
        return new ObsidianCharmaw(this);
    }
}

enum ObsidianCharmawValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_OPPONENTS_PERMANENT,
                        sourceAbility.getControllerId(), sourceAbility, game
                ).stream()
                .filter(Objects::nonNull)
                .filter(permanent1 -> permanent1.isLand(game))
                .filter(permanent -> permanent
                        .getAbilities()
                        .getActivatedManaAbilities(Zone.BATTLEFIELD)
                        .stream()
                        .anyMatch(ability -> ability.getProducableManaTypes(game).contains(ManaType.COLORLESS)))
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public ObsidianCharmawValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "land your opponents control that could produce {C}";
    }
}