package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GnottvoldRecluse extends CardImpl {

    public GnottvoldRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private GnottvoldRecluse(final GnottvoldRecluse card) {
        super(card);
    }

    @Override
    public GnottvoldRecluse copy() {
        return new GnottvoldRecluse(this);
    }
}
