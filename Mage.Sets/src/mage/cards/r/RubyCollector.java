package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class RubyCollector extends CardImpl {

    public RubyCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When you attack with three or more creatures, conjure a card named Mox Ruby into your hand. This ability triggers only once.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            new ConjureCardEffect("Mox Ruby"), 3
        ).setTriggersLimitEachGame(1).setTriggerPhrase("When you attack with three or more creatures, "));

        // {1}{R}: Creatures you control get +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BoostControlledEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")
        ));
    }

    private RubyCollector(final RubyCollector card) {
        super(card);
    }

    @Override
    public RubyCollector copy() {
        return new RubyCollector(this);
    }
}
