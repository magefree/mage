package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ThievesFortune extends CardImpl {

    public ThievesFortune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.INSTANT}, "{2}{U}");
        this.subtype.add(SubType.ROGUE);

        // Prowl {U}
        this.addAbility(new ProwlAbility(this, "{U}"));

        // Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(4, 1, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private ThievesFortune(final ThievesFortune card) {
        super(card);
    }

    @Override
    public ThievesFortune copy() {
        return new ThievesFortune(this);
    }
}
