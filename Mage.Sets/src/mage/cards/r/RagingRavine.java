

package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.custom.ElementalCreatureToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class RagingRavine extends CardImpl {

    public RagingRavine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // Raging Ravine enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // Tap: Add Red or Green.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new RedManaAbility());
        Effect effect = new BecomesCreatureSourceEffect(
                new ElementalCreatureToken(3, 3, "3/3 red and green Elemental creature", new ObjectColor("RG")),
                "land", Duration.EndOfTurn);
        effect.setText("Until end of turn, {this} becomes a 3/3 red and green Elemental creature");
        // {2}{R}{G}: Until end of turn, Raging Ravine becomes a 3/3 red and green Elemental creature with "Whenever this creature attacks, put a +1/+1 counter on it." It's still a land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}{R}{G}"));
        effect = new GainAbilitySourceEffect(new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false), Duration.EndOfTurn);
        effect.setText("with \"Whenever this creature attacks, put a +1/+1 counter on it.\" It's still a land");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private RagingRavine(final RagingRavine card) {
        super(card);
    }

    @Override
    public RagingRavine copy() {
        return new RagingRavine(this);
    }

}