package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
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
import mage.constants.TargetController;
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

    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("another permanent");
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");

    static {
        filter2.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public PiousEvangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{2}{W}",
                "Wayward Disciple",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "B"
        );

        // Pious Evangel
        this.getLeftHalfCard().setPT(2, 2);

        // Whenever Pious Evangel or another creature you control enters, you gain 1 life.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(new GainLifeEffect(1),
                StaticFilters.FILTER_PERMANENT_CREATURE, false, true));

        // {2}, {T}, Sacrifice another permanent: Transform Pious Evangel.
        Ability ability = new SimpleActivatedAbility(new TransformSourceEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter2));
        this.getLeftHalfCard().addAbility(ability);

        // Wayward Disciple
        this.getRightHalfCard().setPT(2, 4);

        // Whenever Wayward Disciple or another creature you control dies, target opponent loses 1 life and you gain 1 life.
        Ability abilityBack = new DiesThisOrAnotherTriggeredAbility(new LoseLifeTargetEffect(1), false, filter);
        abilityBack.addEffect(new GainLifeEffect(1).concatBy("and"));
        abilityBack.addTarget(new TargetOpponent());
        this.getRightHalfCard().addAbility(abilityBack);
    }

    private PiousEvangel(final PiousEvangel card) {
        super(card);
    }

    @Override
    public PiousEvangel copy() {
        return new PiousEvangel(this);
    }
}
