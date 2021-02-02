
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WoodcuttersGrit extends CardImpl {

    public WoodcuttersGrit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Target creature you control gets +3/+3 and gains hexproof until end of turn. (It can't be the target of spells or abilities your opponents control.)</i>
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Effect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn);
        effect.setText("Target creature you control gets +3/+3");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains hexproof until end of turn. <i>(It can't be the target of spells or abilities your opponents control.)</i>");
        this.getSpellAbility().addEffect(effect);
    }

    private WoodcuttersGrit(final WoodcuttersGrit card) {
        super(card);
    }

    @Override
    public WoodcuttersGrit copy() {
        return new WoodcuttersGrit(this);
    }
}
