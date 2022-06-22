
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Mirrorpool extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell you control");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public Mirrorpool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Mirrorpool enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
            // {2}{C}, {T}, Sacrifice Mirrorpool: Copy target instant or sorcery spell you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), new ManaCostsImpl<>("{2}{C}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
        
        // {4}{C}, {T}, Sacrifice Mirrorpool: Create a token that's a copy of target creature you control.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenCopyTargetEffect(), new ManaCostsImpl<>("{4}{C}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private Mirrorpool(final Mirrorpool card) {
        super(card);
    }

    @Override
    public Mirrorpool copy() {
        return new Mirrorpool(this);
    }
}
