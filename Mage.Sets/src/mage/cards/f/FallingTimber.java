
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public final class FallingTimber extends CardImpl {

    private final UUID originalId;

    public FallingTimber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Kicker-Sacrifice a land.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, new FilterControlledLandPermanent("a land"), true))));
        // Prevent all combat damage target creature would deal this turn. If Falling Timber was kicked, prevent all combat damage another target creature would deal this turn.
        Effect effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage target creature would deal this turn. if this spell was kicked, prevent all combat damage another target creature would deal this turn.");
        this.getSpellAbility().addEffect(effect);
        originalId = this.getSpellAbility().getOriginalId();
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if(ability.getOriginalId().equals(originalId)) {
             ability.addTarget(new TargetCreaturePermanent(KickedCondition.instance.apply(game, ability) ? 2 : 1));
        }
    }

    public FallingTimber(final FallingTimber card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public FallingTimber copy() {
        return new FallingTimber(this);
    }
}
