package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShopkeepersBane extends CardImpl {

    public ShopkeepersBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BADGER);
        this.subtype.add(SubType.PEST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature attacks, you gain 2 life.
        this.addAbility(new AttacksTriggeredAbility(new GainLifeEffect(2)));
    }

    private ShopkeepersBane(final ShopkeepersBane card) {
        super(card);
    }

    @Override
    public ShopkeepersBane copy() {
        return new ShopkeepersBane(this);
    }
}
