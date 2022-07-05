
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.RevealHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class ThoughtcutterAgent extends CardImpl {

    public ThoughtcutterAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{U}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {U}{B}, {tap}: Target player loses 1 life and reveals their hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), new ManaCostsImpl<>("{U}{B}"));
        ability.addCost(new TapSourceCost());
        
        Effect revealEffect = new RevealHandTargetEffect(TargetController.ANY);
        revealEffect.setText("and reveals their hand");
        ability.addEffect(revealEffect);
        
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ThoughtcutterAgent(final ThoughtcutterAgent card) {
        super(card);
    }

    @Override
    public ThoughtcutterAgent copy() {
        return new ThoughtcutterAgent(this);
    }
}
