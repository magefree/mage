package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MyrKinsmith extends CardImpl {

    private static final FilterCard filter = new FilterCard("Myr card");

    static {
        filter.add(SubType.MYR.getPredicate());
    }

    public MyrKinsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.MYR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Myr Kinsmith enters the battlefield, you may search your library for a Myr card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true
        ), true));
    }

    private MyrKinsmith(final MyrKinsmith card) {
        super(card);
    }

    @Override
    public MyrKinsmith copy() {
        return new MyrKinsmith(this);
    }
}
