package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonMoose extends CardImpl {

    public DragonMoose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private DragonMoose(final DragonMoose card) {
        super(card);
    }

    @Override
    public DragonMoose copy() {
        return new DragonMoose(this);
    }
}
