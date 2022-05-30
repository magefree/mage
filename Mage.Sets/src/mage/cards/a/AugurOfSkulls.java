
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class AugurOfSkulls extends CardImpl {

    public AugurOfSkulls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{B}: Regenerate Augur of Skulls.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{B}")));
        // Sacrifice Augur of Skulls: Target player discards two cards. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, 
                new DiscardTargetEffect(2),
                new SacrificeSourceCost(),
                new IsStepCondition(PhaseStep.UPKEEP),
                null
        );       
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private AugurOfSkulls(final AugurOfSkulls card) {
        super(card);
    }

    @Override
    public AugurOfSkulls copy() {
        return new AugurOfSkulls(this);
    }
}
