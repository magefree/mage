package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WolfCoveVillager extends CardImpl {

    public WolfCoveVillager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This creature enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private WolfCoveVillager(final WolfCoveVillager card) {
        super(card);
    }

    @Override
    public WolfCoveVillager copy() {
        return new WolfCoveVillager(this);
    }
}
