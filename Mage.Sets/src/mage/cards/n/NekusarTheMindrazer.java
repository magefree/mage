
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class NekusarTheMindrazer extends CardImpl {

    public NekusarTheMindrazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of each player's draw step, that player draws an additional card.
        Effect effect = new DrawCardTargetEffect(1);
        effect.setText("that player draws an additional card");
        this.addAbility(new BeginningOfDrawTriggeredAbility(effect , TargetController.ANY, false));
        // Whenever an opponent draws a card, Nekusar, the Mindrazer deals 1 damage to that player.
        this.addAbility(new DrawCardOpponentTriggeredAbility(new DamageTargetEffect(1, true, "that player"), false, true));
    }

    private NekusarTheMindrazer(final NekusarTheMindrazer card) {
        super(card);
    }

    @Override
    public NekusarTheMindrazer copy() {
        return new NekusarTheMindrazer(this);
    }
}
