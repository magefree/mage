package mage.cards.l;

import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class Lunge extends CardImpl {

    public Lunge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Lunge deals 2 damage to target creature and 2 damage to target player.
        this.getSpellAbility().addEffect(new DamageTargetAndTargetEffect(2, 2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker().setTargetTag(2));

    }

    private Lunge(final Lunge card) {
        super(card);
    }

    @Override
    public Lunge copy() {
        return new Lunge(this);
    }
}
