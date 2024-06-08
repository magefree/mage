package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SuspectSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RubblebeltBraggart extends CardImpl {

    public RubblebeltBraggart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Rubblebelt Braggart attacks, if it's not suspected, you may suspect it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new SuspectSourceEffect(), true),
                RubblebeltBraggartCondition.instance, "Whenever {this} attacks, " +
                "if it's not suspected, you may suspect it."
        ));
    }

    private RubblebeltBraggart(final RubblebeltBraggart card) {
        super(card);
    }

    @Override
    public RubblebeltBraggart copy() {
        return new RubblebeltBraggart(this);
    }
}

enum RubblebeltBraggartCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(permanent -> !permanent.isSuspected())
                .orElse(false);
    }
}
