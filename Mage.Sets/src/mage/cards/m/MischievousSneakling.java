package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MischievousSneakling extends CardImpl {

    public MischievousSneakling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/B}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Flash
        this.addAbility(FlashAbility.getInstance());
    }

    private MischievousSneakling(final MischievousSneakling card) {
        super(card);
    }

    @Override
    public MischievousSneakling copy() {
        return new MischievousSneakling(this);
    }
}
