package mage.cards.w;

import mage.MageInt;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WingShredder extends CardImpl {

    public WingShredder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
        this.color.setGreen(true);
        this.nightCard = true;
        this.transformable = true;

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Nightbound
        this.addAbility(NightboundAbility.getInstance());
    }

    private WingShredder(final WingShredder card) {
        super(card);
    }

    @Override
    public WingShredder copy() {
        return new WingShredder(this);
    }
}
