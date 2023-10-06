package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
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
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{3}{R}{R}")));
    
        // When Unstable Hulk is turned face up, it gets +6/+6 and gains trample until end of turn. You skip your next turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new BoostSourceEffect(6,6,Duration.EndOfTurn)
                .setText("it gets +6/+6"));
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains trample until end of turn"));
        ability.addEffect(new SkipNextTurnSourceEffect());
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
