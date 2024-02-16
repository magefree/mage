package mage.cards.a;

import java.util.UUID;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author weirddan455
 */
public final class AggressiveSabotage extends CardImpl {

    public AggressiveSabotage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));

        // Target player discards two cards. If this spell was kicked, it deals 3 damage to that player.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(3),
                KickedCondition.ONCE,
                "If this spell was kicked, it deals 3 damage to that player."
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private AggressiveSabotage(final AggressiveSabotage card) {
        super(card);
    }

    @Override
    public AggressiveSabotage copy() {
        return new AggressiveSabotage(this);
    }
}
