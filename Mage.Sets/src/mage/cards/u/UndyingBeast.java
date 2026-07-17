package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class UndyingBeast extends CardImpl {

    public UndyingBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Undying Beast dies, put it on top of its owner's library.
        this.addAbility(new DiesSourceTriggeredAbility(new PutOnLibraryTargetEffect(true)
                .setText("put it on top of its owner's library"), false, SetTargetPointer.CARD));
    }

    private UndyingBeast(final UndyingBeast card) {
        super(card);
    }

    @Override
    public UndyingBeast copy() {
        return new UndyingBeast(this);
    }
}
