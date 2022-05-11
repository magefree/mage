
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 * @author nantuko, BetaSteward_at_googlemail.com
 */
public final class AetherFigment extends CardImpl {

    public AetherFigment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Aether Figment can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // If Aether Figment was kicked, it enters the battlefield with two +1/+1 counters on it
        Ability ability = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                KickedCondition.instance,
                "If {this} was kicked, it enters the battlefield with two +1/+1 counters on it.",
                "");
        this.addAbility(ability);
    }

    private AetherFigment(final AetherFigment card) {
        super(card);
    }

    @Override
    public AetherFigment copy() {
        return new AetherFigment(this);
    }

}
