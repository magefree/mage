package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.AffinityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrineGiant extends CardImpl {

    public BrineGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Affinity for enchantments
        this.addAbility(new AffinityAbility(AffinityType.ENCHANTMENTS));
    }

    private BrineGiant(final BrineGiant card) {
        super(card);
    }

    @Override
    public BrineGiant copy() {
        return new BrineGiant(this);
    }
}
