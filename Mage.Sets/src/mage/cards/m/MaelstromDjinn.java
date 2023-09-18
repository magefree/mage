
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.VanishingSacrificeAbility;
import mage.abilities.keyword.VanishingUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox
 */
public final class MaelstromDjinn extends CardImpl {

    public MaelstromDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{U}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Morph {2}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{U}")));
        // When Maelstrom Djinn is turned face up, put two time counters on it and it gains vanishing.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(2)));
        Effect effect = new GainAbilitySourceEffect(new VanishingUpkeepAbility(0), Duration.WhileOnBattlefield);
        effect.setText("and it gains vanishing");
        ability.addEffect(effect);
        effect = new GainAbilitySourceEffect(new VanishingSacrificeAbility(), Duration.WhileOnBattlefield);
        effect.setText("");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private MaelstromDjinn(final MaelstromDjinn card) {
        super(card);
    }

    @Override
    public MaelstromDjinn copy() {
        return new MaelstromDjinn(this);
    }
}
