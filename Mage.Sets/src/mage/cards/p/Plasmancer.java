package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Plasmancer extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Swamp card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.SWAMP.getPredicate());
    }

    public Plasmancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.NECRON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Dynastic Advisor -- When Plasmancer enters the battlefield, search your library for a basic Swamp card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ).withFlavorWord("Dynastic Advisor"));
    }

    private Plasmancer(final Plasmancer card) {
        super(card);
    }

    @Override
    public Plasmancer copy() {
        return new Plasmancer(this);
    }
}
