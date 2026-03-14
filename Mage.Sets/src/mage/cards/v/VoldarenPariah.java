package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class VoldarenPariah extends TransformingDoubleFacedCard {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public VoldarenPariah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE, SubType.HORROR}, "{3}{B}{B}",
                "Abolisher of Bloodlines",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.VAMPIRE}, ""
        );

        // Voldaren Pariah
        this.getLeftHalfCard().setPT(3, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Sacrifice three other creatures: Transform Voldaren Pariah.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(),
                new SacrificeTargetCost(3, filter)));

        // Madness {B}{B}{B}
        this.getLeftHalfCard().addAbility(new MadnessAbility(new ManaCostsImpl<>("{B}{B}{B}")));

        // Abolisher of Bloodlines
        this.getRightHalfCard().setPT(6, 5);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // When this creature transforms into Abolisher of Bloodlines, target opponent sacrifices three creatures.
        Ability ability = new TransformIntoSourceTriggeredAbility(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURES, 3, "target opponent"
        ));
        ability.addTarget(new TargetOpponent());
        this.getRightHalfCard().addAbility(ability);
    }

    private VoldarenPariah(final VoldarenPariah card) {
        super(card);
    }

    @Override
    public VoldarenPariah copy() {
        return new VoldarenPariah(this);
    }
}
