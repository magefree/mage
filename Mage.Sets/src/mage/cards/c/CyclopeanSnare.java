
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class CyclopeanSnare extends CardImpl {

    public CyclopeanSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {3}, {tap}: Tap target creature, then return Cyclopean Snare to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new ReturnToHandSourceEffect(true).concatBy(", then"));
        this.addAbility(ability);
    }

    private CyclopeanSnare(final CyclopeanSnare card) {
        super(card);
    }

    @Override
    public CyclopeanSnare copy() {
        return new CyclopeanSnare(this);
    }
}
