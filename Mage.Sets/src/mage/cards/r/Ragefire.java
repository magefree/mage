package mage.cards.r;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ragefire extends CardImpl {

    public Ragefire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Ragefire deals 3 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Ragefire(final Ragefire card) {
        super(card);
    }

    @Override
    public Ragefire copy() {
        return new Ragefire(this);
    }
}
