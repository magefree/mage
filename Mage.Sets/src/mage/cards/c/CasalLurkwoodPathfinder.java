package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CasalLurkwoodPathfinder extends TransformingDoubleFacedCard {

    private static final FilterLandCard filter = new FilterLandCard("Forest card");
    private static final FilterCreaturePermanent filterLegendary = new FilterCreaturePermanent();

    static {
        filter.add(SubType.FOREST.getPredicate());
        filterLegendary.add(SuperType.LEGENDARY.getPredicate());
    }

    public CasalLurkwoodPathfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.TIEFLING, SubType.DRUID}, "{3}{G}",
                "Casal, Pathbreaker Owlbear",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BIRD, SubType.BEAR}, "G"
        );

        // Casal, Lurkwood Pathfinder
        this.getLeftHalfCard().setPT(3, 3);

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // When Casal, Lurkwood Pathfinder enters the battlefield, search your library for a Forest card, put it into the battlefield tapped, then shuffle.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true)
        ));

        // Whenever Casal attacks, you may pay {1}{G}. If you do, transform her.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new TransformSourceEffect().setText("transform her"), new ManaCostsImpl<>("{1}{G}")
        )));

        // Casal, Pathbreaker Owlbear
        this.getRightHalfCard().setPT(6, 6);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // When this creature transforms into Casal, Pathbreaker Owlbear, other legendary creatures you control get +2/+2 and gain trample until end of turn.
        Ability ability = new TransformIntoSourceTriggeredAbility(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn, filterLegendary, true
        ).setText("other legendary creatures you control get +2/+2"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, filterLegendary, true
        ).setText("and gain trample until end of turn"));
        this.getRightHalfCard().addAbility(ability);

        // At the beginning of your upkeep, transform Casal.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect()));
    }

    private CasalLurkwoodPathfinder(final CasalLurkwoodPathfinder card) {
        super(card);
    }

    @Override
    public CasalLurkwoodPathfinder copy() {
        return new CasalLurkwoodPathfinder(this);
    }
}
