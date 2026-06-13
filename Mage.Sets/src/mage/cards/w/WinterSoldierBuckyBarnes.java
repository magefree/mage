package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WinterSoldierBuckyBarnes extends CardImpl {

    public WinterSoldierBuckyBarnes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Winter Soldier enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private WinterSoldierBuckyBarnes(final WinterSoldierBuckyBarnes card) {
        super(card);
    }

    @Override
    public WinterSoldierBuckyBarnes copy() {
        return new WinterSoldierBuckyBarnes(this);
    }
}
