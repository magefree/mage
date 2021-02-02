
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class ViciousConquistador extends CardImpl {

    public ViciousConquistador(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Vicious Conquistador attacks, each opponent loses 1 life.
        this.addAbility(new AttacksTriggeredAbility(new LoseLifeOpponentsEffect(1), false));
    }

    private ViciousConquistador(final ViciousConquistador card) {
        super(card);
    }

    @Override
    public ViciousConquistador copy() {
        return new ViciousConquistador(this);
    }
}
