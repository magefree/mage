
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class VirulentSwipe extends CardImpl {

    public VirulentSwipe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Target creature gets +2/+0 and gains deathtouch until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());        
        Effect effect = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        effect.setText("Target creature gets +2/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains deathtouch until end of turn");
        this.getSpellAbility().addEffect(effect);

        this.addAbility(new ReboundAbility());
    }

    private VirulentSwipe(final VirulentSwipe card) {
        super(card);
    }

    @Override
    public VirulentSwipe copy() {
        return new VirulentSwipe(this);
    }
}
