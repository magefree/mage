package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
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
public final class StrikeCannon extends CardImpl {

    public StrikeCannon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");
        

        // Strike Cannon deals X damage to target creature or player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());

        // Salvage {3}
        this.addAbility(new SalvageAbility(new ManaCostsImpl("{3}")));
    }

    public StrikeCannon(final StrikeCannon card) {
        super(card);
    }

    @Override
    public StrikeCannon copy() {
        return new StrikeCannon(this);
    }
}
