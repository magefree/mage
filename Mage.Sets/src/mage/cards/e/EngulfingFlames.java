
package mage.cards.e;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ruleModifying.CantRegenerateTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33, North
 */
public final class EngulfingFlames extends CardImpl {

    public EngulfingFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Engulfing Flames deals 1 damage to target creature. It can't be regenerated this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CantRegenerateTargetEffect(Duration.EndOfTurn, "It"));
        // Flashback {3}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{3}{R}")));
    }

    private EngulfingFlames(final EngulfingFlames card) {
        super(card);
    }

    @Override
    public EngulfingFlames copy() {
        return new EngulfingFlames(this);
    }
}
