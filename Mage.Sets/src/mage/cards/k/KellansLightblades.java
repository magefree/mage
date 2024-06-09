package mage.cards.k;

import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.common.BargainCostWasPaidHint;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KellansLightblades extends CardImpl {

    public KellansLightblades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Bargain
        this.addAbility(new BargainAbility());

        // Kellan's Lightblades deals 3 damage to target attacking or blocking creature. If this spell was bargained, destroy that creature instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DestroyTargetEffect(),
                new DamageTargetEffect(3),
                BargainedCondition.instance,
                "{this} deals 3 damage to target attacking or blocking creature. "
                        + "If this spell was bargained, destroy that creature instead."
        ));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addHint(BargainCostWasPaidHint.instance);
    }

    private KellansLightblades(final KellansLightblades card) {
        super(card);
    }

    @Override
    public KellansLightblades copy() {
        return new KellansLightblades(this);
    }
}
