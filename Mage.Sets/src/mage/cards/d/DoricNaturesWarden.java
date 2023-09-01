package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
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
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoricNaturesWarden extends TransformingDoubleFacedCard {

    private static final FilterLandCard filter = new FilterLandCard("Forest card");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();

    static {
        filter.add(SubType.FOREST.getPredicate());
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    public DoricNaturesWarden(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.TIEFLING, SubType.DRUID}, "{3}{G}",
                "Doric, Owlbear Avenger",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BIRD, SubType.BEAR}, "G"
        );
        this.getLeftHalfCard().setPT(3, 3);
        this.getRightHalfCard().setPT(6, 6);

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // When Doric, Nature's Warden enters the battlefield, search your library for a Forest card, put it into the battlefield tapped, then shuffle.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true, true)
        ));

        // Whenever Doric attacks, you may pay {1}{G}. If you do, transform her.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new TransformSourceEffect().setText("transform her"), new ManaCostsImpl<>("{1}{G}")
        )));

        // Doric, Owlbear Avenger
        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // When this creature transforms into Doric, Owlbear Avenger, other legendary creatures you control get +2/+2 and gain trample until end of turn.
        Ability ability = new TransformIntoSourceTriggeredAbility(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn, filter2, true
        ).setText("other legendary creatures you control get +2/+2"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, filter2, true
        ).setText("and gain trample until end of turn"));
        this.getRightHalfCard().addAbility(ability);

        // At the beginning of your upkeep, transform Doric.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TransformSourceEffect(), TargetController.YOU, false
        ));
    }

    private DoricNaturesWarden(final DoricNaturesWarden card) {
        super(card);
    }

    @Override
    public DoricNaturesWarden copy() {
        return new DoricNaturesWarden(this);
    }
}
