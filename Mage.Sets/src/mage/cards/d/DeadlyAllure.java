
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward
 */
public final class DeadlyAllure extends CardImpl {

    public DeadlyAllure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target creature gains deathtouch until end of turn and must be blocked this turn if able.        
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn));
        Effect effect = new MustBeBlockedByAtLeastOneTargetEffect(Duration.EndOfTurn);
        effect.setText("and must be blocked this turn if able");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Flashback {G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{G}")));

    }

    private DeadlyAllure(final DeadlyAllure card) {
        super(card);
    }

    @Override
    public DeadlyAllure copy() {
        return new DeadlyAllure(this);
    }
}

