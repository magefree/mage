package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DarkRevenant extends CardImpl {

    public DarkRevenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Dark Revenant dies, put it on top of its owner's library.
        this.addAbility(new DiesSourceTriggeredAbility(new PutOnLibraryTargetEffect(true)
                .setText("put it on top of its owner's library"), false, SetTargetPointer.CARD));
    }

    private DarkRevenant(final DarkRevenant card) {
        super(card);
    }

    @Override
    public DarkRevenant copy() {
        return new DarkRevenant(this);
    }
}
