package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChaosTheEndless extends CardImpl {

    public ChaosTheEndless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Chaos dies, put it on the bottom of its owner's library.
        this.addAbility(new DiesSourceTriggeredAbility(new PutOnLibrarySourceEffect(
                false, "put it on the bottom of its owner's library"
        ), false));
    }

    private ChaosTheEndless(final ChaosTheEndless card) {
        super(card);
    }

    @Override
    public ChaosTheEndless copy() {
        return new ChaosTheEndless(this);
    }
}
