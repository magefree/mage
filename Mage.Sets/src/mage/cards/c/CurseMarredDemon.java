package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.target.common.TargetCardInLibrary;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CurseMarredDemon extends CardImpl {

    public CurseMarredDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this creature enters, search your library for a card, put it into your hand, shuffle, then discard a card at random.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false).setText("search your library for a card, put it into your hand, shuffle")
        );
        ability.addEffect(new DiscardControllerEffect(1, true).setText(", then discard a card at random"));
        this.addAbility(ability);
    }

    private CurseMarredDemon(final CurseMarredDemon card) {
        super(card);
    }

    @Override
    public CurseMarredDemon copy() {
        return new CurseMarredDemon(this);
    }
}
