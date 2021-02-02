
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class RatsOfRath extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact, creature, or land you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public RatsOfRath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.RAT);
        
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        
        // {B}: Destroy target artifact, creature, or land you control.
        Effect effect = new DestroyTargetEffect();
        effect.setOutcome(Outcome.AIDontUseIt); // AI can't handle this
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ColoredManaCost(ColoredManaSymbol.B));
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    private RatsOfRath(final RatsOfRath card) {
        super(card);
    }

    @Override
    public RatsOfRath copy() {
        return new RatsOfRath(this);
    }
}
