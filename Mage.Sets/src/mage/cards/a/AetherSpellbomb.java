
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public final class AetherSpellbomb extends CardImpl {

    public AetherSpellbomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        //{U}, Sacrifice Aether Spellbomb: Return target creature to its owner's hand.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        //{1}, Sacrifice Aether Spellbomb: Draw a card.
        SimpleActivatedAbility drawCardAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        drawCardAbility.addCost(new SacrificeSourceCost());
        this.addAbility(drawCardAbility);

    }

    private AetherSpellbomb(final AetherSpellbomb card) {
        super(card);
    }

    @Override
    public AetherSpellbomb copy() {
        return new AetherSpellbomb(this);
    }
}