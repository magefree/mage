
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PiousEvangel extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("{this} or another creature");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("another permanent");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public PiousEvangel(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{2}{W}",
                "Wayward Disciple",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "B"
        );
        this.getLeftHalfCard().setPT(2, 2);
        this.getRightHalfCard().setPT(2, 4);

        // Whenever Pious Evangel or another creature enters the battlefield under your control, you gain 1 life.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), filter));

        // {2}, {T}, Sacrifice another permanent: Transform Pious Evangel.
        Ability ability = new SimpleActivatedAbility(new TransformSourceEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter2));
        this.getLeftHalfCard().addAbility(ability);

        // Wayward Disciple
        // Whenever Wayward Disciple or another creature you control dies, target opponent loses 1 life and you gain 1 life.
        ability = new DiesThisOrAnotherCreatureTriggeredAbility(
                new LoseLifeTargetEffect(1), false, StaticFilters.FILTER_CONTROLLED_CREATURE
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.getRightHalfCard().addAbility(ability);
    }

    private PiousEvangel(final PiousEvangel card) {
        super(card);
    }

    @Override
    public PiousEvangel copy() {
        return new PiousEvangel(this);
    }
}