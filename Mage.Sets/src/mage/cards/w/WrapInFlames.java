package mage.cards.w;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class WrapInFlames extends CardImpl {

    public WrapInFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Wrap in Flames deals 1 damage to each of up to three target creatures. Those creatures can't block this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1).setText("{this} deals 1 damage to each of up to three target creatures."));
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn).setText("Those creatures can't block this turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));
    }

    private WrapInFlames(final WrapInFlames card) {
        super(card);
    }

    @Override
    public WrapInFlames copy() {
        return new WrapInFlames(this);
    }
}
