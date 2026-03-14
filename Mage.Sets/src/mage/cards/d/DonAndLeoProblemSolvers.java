package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DonAndLeoProblemSolvers extends CardImpl {

    public DonAndLeoProblemSolvers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W/U}{W/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your end step, exile up to one target artifact you control and up to one target creature you control. Then return them to the battlefield under their owners' control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new ExileThenReturnTargetEffect(false, false)
                        .setText("exile up to one target artifact you control and up to one target creature you control. " +
                                "Then return them to the battlefield under their owners' control")
                        .setTargetPointer(new EachTargetPointer())
        );
        ability.addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        ));
        ability.addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
        this.addAbility(ability);
    }

    private DonAndLeoProblemSolvers(final DonAndLeoProblemSolvers card) {
        super(card);
    }

    @Override
    public DonAndLeoProblemSolvers copy() {
        return new DonAndLeoProblemSolvers(this);
    }
}
