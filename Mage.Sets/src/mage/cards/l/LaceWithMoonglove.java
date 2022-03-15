
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class LaceWithMoonglove extends CardImpl {

    public LaceWithMoonglove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private LaceWithMoonglove(final LaceWithMoonglove card) {
        super(card);
    }

    @Override
    public LaceWithMoonglove copy() {
        return new LaceWithMoonglove(this);
    }
}
