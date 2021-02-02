
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class AngelOfFury extends CardImpl {

    public AngelOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Angel of Fury is put into your graveyard from the battlefield, you may shuffle it into your library.
        this.addAbility(new DiesSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect(), true));
    }

    private AngelOfFury(final AngelOfFury card) {
        super(card);
    }

    @Override
    public AngelOfFury copy() {
        return new AngelOfFury(this);
    }
}
