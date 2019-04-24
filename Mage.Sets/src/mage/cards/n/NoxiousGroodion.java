package mage.cards.n;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NoxiousGroodion extends CardImpl {

    public NoxiousGroodion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private NoxiousGroodion(final NoxiousGroodion card) {
        super(card);
    }

    @Override
    public NoxiousGroodion copy() {
        return new NoxiousGroodion(this);
    }
}
