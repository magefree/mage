package mage.cards.h;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HorridVigor extends CardImpl {

    public HorridVigor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gains deathtouch and indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                .setText("target creature gains deathtouch"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HorridVigor(final HorridVigor card) {
        super(card);
    }

    @Override
    public HorridVigor copy() {
        return new HorridVigor(this);
    }
}
