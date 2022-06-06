
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author ciaccona007
 */
public final class HashepOasis extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Desert");

    static {
        filter.add(SubType.DESERT.getPredicate());
    }
    public HashepOasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.subtype.add(SubType.DESERT);

        // {t}: Add {C}.
        addAbility(new ColorlessManaAbility());
        
        // {t}, Pay 1 life: Add {G}.
        Ability ability = new GreenManaAbility();
        ability.addCost(new PayLifeCost(1));
        addAbility(ability);
        
        // {1}{G}{G}, {t}, Sacrifice a Desert: Target creature gets +3/+3 until end of turn. Activate this ability only any time you could cast a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(3,3,Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        addAbility(ability);
    }

    private HashepOasis(final HashepOasis card) {
        super(card);
    }

    @Override
    public HashepOasis copy() {
        return new HashepOasis(this);
    }
}
