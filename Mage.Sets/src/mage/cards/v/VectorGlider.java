package mage.cards.v;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VectorGlider extends CardImpl {

    public VectorGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setBlue(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private VectorGlider(final VectorGlider card) {
        super(card);
    }

    @Override
    public VectorGlider copy() {
        return new VectorGlider(this);
    }
}
