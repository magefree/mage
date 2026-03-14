package mage.cards.p;

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
public final class PunishTheEnemy extends CardImpl {

    public PunishTheEnemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Punish the Enemy deals 3 damage to target player and 3 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetAndTargetEffect(3, 3));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(2));
    }

    private PunishTheEnemy(final PunishTheEnemy card) {
        super(card);
    }

    @Override
    public PunishTheEnemy copy() {
        return new PunishTheEnemy(this);
    }

}
