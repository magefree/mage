package mage.cards.g;

import java.util.UUID;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SalvageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author NinthWorld
 */
public final class GaussRifle extends CardImpl {

    public GaussRifle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");
        

        // Gauss Rifle deals 2 damage to target creature or player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());

        // Salvage {3}
        this.addAbility(new SalvageAbility(new ManaCostsImpl("{3}")));
    }

    public GaussRifle(final GaussRifle card) {
        super(card);
    }

    @Override
    public GaussRifle copy() {
        return new GaussRifle(this);
    }
}
