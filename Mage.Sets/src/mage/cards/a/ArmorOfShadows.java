package mage.cards.a;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmorOfShadows extends CardImpl {

    public ArmorOfShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Until end of turn, target creature gets +1/+0 and gains indestructible.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
                .setText("until end of turn, target creature gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains indestructible"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ArmorOfShadows(final ArmorOfShadows card) {
        super(card);
    }

    @Override
    public ArmorOfShadows copy() {
        return new ArmorOfShadows(this);
    }
}
