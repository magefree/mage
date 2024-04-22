
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class PyriteSpellbomb extends CardImpl {

    public PyriteSpellbomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2, "it"), new ColoredManaCost(ColoredManaSymbol.R));
        firstAbility.addCost(new SacrificeSourceCost());
        firstAbility.addTarget(new TargetAnyTarget());
        this.addAbility(firstAbility);
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        secondAbility.addCost(new SacrificeSourceCost());
        this.addAbility(secondAbility);
    }

    private PyriteSpellbomb(final PyriteSpellbomb card) {
        super(card);
    }

    @Override
    public PyriteSpellbomb copy() {
        return new PyriteSpellbomb(this);
    }
}
