
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziScionToken;

/**
 *
 * @author LevelX2
 */
public final class BroodMonitor extends CardImpl {

    public BroodMonitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.ELDRAZI, SubType.DRONE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // When Brood Monitor enters the battlefield, create three 1/1 colorless Eldrazi Scion creature tokens. They have "Sacrifice this creature: Add {C}."
        Effect effect = new CreateTokenEffect(new EldraziScionToken(), 3);
        effect.setText("create three 1/1 colorless Eldrazi Scion creature tokens. They have \"Sacrifice this creature: Add {C}.\"");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));

    }

    private BroodMonitor(final BroodMonitor card) {
        super(card);
    }

    @Override
    public BroodMonitor copy() {
        return new BroodMonitor(this);
    }
}
