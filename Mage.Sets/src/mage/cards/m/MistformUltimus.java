package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author vereena42
 */
public final class MistformUltimus extends CardImpl {

    public MistformUltimus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mistform Ultimus is every creature type.
        this.addAbility(new ChangelingAbility(false));
    }

    private MistformUltimus(final MistformUltimus card) {
        super(card);
    }

    @Override
    public MistformUltimus copy() {
        return new MistformUltimus(this);
    }
}
