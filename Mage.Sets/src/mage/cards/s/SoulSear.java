package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulSear extends CardImpl {

    public SoulSear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Soul Sear deals 5 damage to target creature or planeswalker. That permanent loses indestructible until end of turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new LoseAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("That permanent loses indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private SoulSear(final SoulSear card) {
        super(card);
    }

    @Override
    public SoulSear copy() {
        return new SoulSear(this);
    }
}
