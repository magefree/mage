package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class PitilessGorgon extends CardImpl {

    public PitilessGorgon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/G}{B/G}");

        this.subtype.add(SubType.GORGON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private PitilessGorgon(final PitilessGorgon card) {
        super(card);
    }

    @Override
    public PitilessGorgon copy() {
        return new PitilessGorgon(this);
    }
}
