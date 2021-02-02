
package mage.cards.e;

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
public final class EyelessWatcher extends CardImpl {

    public EyelessWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // When Eyeless Watcher enters the battlefield, create two 1/1 colorless Eldrazi Scion creature tokens. They have "Sacrifice this creature: Add {C}."
        Effect effect = new CreateTokenEffect(new EldraziScionToken(), 2);
        effect.setText("create two 1/1 colorless Eldrazi Scion creature tokens. They have \"Sacrifice this creature: Add {C}.\"");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));

    }

    private EyelessWatcher(final EyelessWatcher card) {
        super(card);
    }

    @Override
    public EyelessWatcher copy() {
        return new EyelessWatcher(this);
    }
}
