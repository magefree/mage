package mage.cards.b;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattleRageBlessing extends CardImpl {

    public BattleRageBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gains deathtouch and indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                .setText("target creature gains deathtouch"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and indestructible until end of turn"));
    }

    private BattleRageBlessing(final BattleRageBlessing card) {
        super(card);
    }

    @Override
    public BattleRageBlessing copy() {
        return new BattleRageBlessing(this);
    }
}
