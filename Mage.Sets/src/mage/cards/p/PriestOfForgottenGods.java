package mage.cards.p;

import mage.MageInt;
import mage.Mana;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPlayer;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

/**
 * @author TheElk801
 */
public final class PriestOfForgottenGods extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public PriestOfForgottenGods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}, Sacrifice two other creatures: Any number of target players each lose 2 life and sacrifice a creature. You add {B}{B} and draw a card.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeTargetEffect(2)
                        .setText("Any number of target players each lose 2 life"),
                new TapSourceCost()
        );
        ability.addEffect(
                new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "")
                        .setText("and sacrifice a creature")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, true)));
        ability.addEffect(new BasicManaEffect(Mana.BlackMana(2)).setText("You add {B}{B}"));
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("and draw a card"));
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);
    }

    private PriestOfForgottenGods(final PriestOfForgottenGods card) {
        super(card);
    }

    @Override
    public PriestOfForgottenGods copy() {
        return new PriestOfForgottenGods(this);
    }
}