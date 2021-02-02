
package mage.cards.f;

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
public final class FeralAbomination extends CardImpl {

    public FeralAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        
        this.subtype.add(SubType.THRULL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

    }

    private FeralAbomination(final FeralAbomination card) {
        super(card);
    }

    @Override
    public FeralAbomination copy() {
        return new FeralAbomination(this);
    }
}
