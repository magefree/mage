package mage.cards.v;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenomousChangeling extends CardImpl {

    public VenomousChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private VenomousChangeling(final VenomousChangeling card) {
        super(card);
    }

    @Override
    public VenomousChangeling copy() {
        return new VenomousChangeling(this);
    }
}
