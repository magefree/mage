
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziScionToken;

/**
 *
 * @author LevelX2
 */
public final class CarrierThrall extends CardImpl {

    public CarrierThrall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Carrier Thrall dies, create a 1/1 colorless Eldrazi Scion creature token. It has "Sacrifice this creature. Add {C}."
        Effect effect = new CreateTokenEffect(new EldraziScionToken());
        effect.setText("create a 1/1 colorless Eldrazi Scion creature token. It has \"Sacrifice this creature: Add {C}.\"");
        this.addAbility(new DiesSourceTriggeredAbility(effect, false));

    }

    private CarrierThrall(final CarrierThrall card) {
        super(card);
    }

    @Override
    public CarrierThrall copy() {
        return new CarrierThrall(this);
    }
}
