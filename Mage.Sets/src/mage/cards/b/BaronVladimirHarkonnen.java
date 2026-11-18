package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class BaronVladimirHarkonnen extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public BaronVladimirHarkonnen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever an opponent sacrifices a nontoken creature, you draw a card.
        this.addAbility(new SacrificePermanentTriggeredAbility(
            new DrawCardSourceControllerEffect(1, true),
            StaticFilters.FILTER_CREATURE_NON_TOKEN,
            TargetController.OPPONENT
        ));

        // {T}, Sacrifice another legendary creature: Target opponent sacrifices a nontoken creature of their choice and loses 3 life.
        Ability ability = new SimpleActivatedAbility(
            new SacrificeEffect(StaticFilters.FILTER_CREATURE_NON_TOKEN, 1, "target opponent"),
            new TapSourceCost()
        );
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new LoseLifeTargetEffect(3).setText("and loses 3 life"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BaronVladimirHarkonnen(final BaronVladimirHarkonnen card) {
        super(card);
    }

    @Override
    public BaronVladimirHarkonnen copy() {
        return new BaronVladimirHarkonnen(this);
    }
}
