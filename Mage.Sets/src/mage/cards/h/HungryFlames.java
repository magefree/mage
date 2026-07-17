package mage.cards.h;

import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public final class HungryFlames extends CardImpl {

    public HungryFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Hungry Flames deals 3 damage to target creature and 2 damage to target player or planeswalker.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker().setTargetTag(2));
        this.getSpellAbility().addEffect(new DamageTargetAndTargetEffect(3, 2));
    }

    private HungryFlames(final HungryFlames card) {
        super(card);
    }

    @Override
    public HungryFlames copy() {
        return new HungryFlames(this);
    }

}
