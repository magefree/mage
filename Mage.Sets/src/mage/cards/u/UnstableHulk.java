
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.turn.SkipNextTurnSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author BursegSardaukar
 */
public final class UnstableHulk extends CardImpl {

    public UnstableHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Morph {3}{R}{R} 
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{R}{R}")));
    
        //When Unstable Hulk is turned face up, it gets +6/+6 and gains trample until end of turn. You skip your next turn.
        Effect effect = new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect);
        effect = new BoostSourceEffect(6,6,Duration.EndOfTurn);
        ability.addEffect(effect);
        effect = new SkipNextTurnSourceEffect();
        ability.addEffect(effect);
        this.addAbility(ability);
    
    }

    private UnstableHulk(final UnstableHulk card) {
        super(card);
    }

    @Override
    public UnstableHulk copy() {
        return new UnstableHulk(this);
    }
}