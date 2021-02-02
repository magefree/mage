
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author dustinconrad
 */
public final class WanderguardSentry extends CardImpl {

    public WanderguardSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.DRONE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Wanderguard Sentry enters the battlefield, look at target opponent's hand.
        Effect effect = new LookAtTargetPlayerHandEffect();
        effect.setText("look at target opponent's hand");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private WanderguardSentry(final WanderguardSentry card) {
        super(card);
    }

    @Override
    public WanderguardSentry copy() {
        return new WanderguardSentry(this);
    }
}
