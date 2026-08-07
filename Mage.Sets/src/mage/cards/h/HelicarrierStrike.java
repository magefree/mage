package mage.cards.h;

import java.util.UUID;
import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author nandmp
 */
public final class HelicarrierStrike extends CardImpl {

    public HelicarrierStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");
        

        // Teamwork 2
        this.addAbility(new TeamworkAbility(2));

        // Helicarrier Strike deals 2 damage to target attacking or blocking creature. If this spell was cast using teamwork, it deals 4 damage to that creature instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(4), new DamageTargetEffect(2),
                TeamworkCondition.instance, "{this} deals 2 damage to target attacking or blocking creature. " +
                "If this spell was cast using teamwork, it deals 4 damage to that creature instead"
        ));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private HelicarrierStrike(final HelicarrierStrike card) {
        super(card);
    }

    @Override
    public HelicarrierStrike copy() {
        return new HelicarrierStrike(this);
    }
}
