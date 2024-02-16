
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class CanopyClaws extends CardImpl {

    public CanopyClaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Target creature loses flying until end of turn.
        this.getSpellAbility().addEffect(new LoseAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Flashback {G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{G}")));
    }

    private CanopyClaws(final CanopyClaws card) {
        super(card);
    }

    @Override
    public CanopyClaws copy() {
        return new CanopyClaws(this);
    }
}
