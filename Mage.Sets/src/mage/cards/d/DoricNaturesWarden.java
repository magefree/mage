package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoricNaturesWarden extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("Forest card");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public DoricNaturesWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.d.DoricOwlbearAvenger.class;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Doric, Nature's Warden enters the battlefield, search your library for a Forest card, put it into the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true)
        ));

        // Whenever Doric attacks, you may pay {1}{G}. If you do, transform her.
        this.addAbility(new TransformAbility());
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new TransformSourceEffect().setText("transform her"), new ManaCostsImpl<>("{1}{G}")
        )));
    }

    private DoricNaturesWarden(final DoricNaturesWarden card) {
        super(card);
    }

    @Override
    public DoricNaturesWarden copy() {
        return new DoricNaturesWarden(this);
    }
}
