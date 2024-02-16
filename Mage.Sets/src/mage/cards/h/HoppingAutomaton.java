
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class HoppingAutomaton extends CardImpl {

    public HoppingAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {0}: Hopping Automaton gets -1/-1 and gains flying until end of turn.
        Effect effect = new BoostSourceEffect(-1, -1, Duration.EndOfTurn);
        effect.setText("{this} gets -1/-1");
        Ability ability = new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{0}"));
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private HoppingAutomaton(final HoppingAutomaton card) {
        super(card);
    }

    @Override
    public HoppingAutomaton copy() {
        return new HoppingAutomaton(this);
    }
}
