
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class LoomingSpires extends CardImpl {

    public LoomingSpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Looming Spires enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // When Looming Spires enters the battlefield, target creature gets +1/+1 and gain first strike until end of turn.
        Effect effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains first strike");
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn), false);
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private LoomingSpires(final LoomingSpires card) {
        super(card);
    }

    @Override
    public LoomingSpires copy() {
        return new LoomingSpires(this);
    }
}
