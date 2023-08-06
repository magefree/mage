
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.LastTimeCounterRemovedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Gal Lerman
 */
public final class Chronozoa extends CardImpl {

    public Chronozoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vanishing 3 (This permanent enters the battlefield with three time counters on it. At the beginning of your upkeep, remove a time counter from it. When the last is removed, sacrifice it.)
        this.addAbility(new VanishingAbility(3));

        // When Chronozoa is put into a graveyard from play, if it had no time counters on it, create two tokens that are copies of it.
        Effect effect = new CreateTokenCopySourceEffect(2);
        effect.setText("create two tokens that are copies of it");
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new DiesSourceTriggeredAbility(effect, false),
                LastTimeCounterRemovedCondition.instance,
                "When {this} dies, if it had no time counters on it, create two tokens that are copies of it."));
    }

    private Chronozoa(final Chronozoa card) {
        super(card);
    }

    @Override
    public Chronozoa copy() {
        return new Chronozoa(this);
    }
}
