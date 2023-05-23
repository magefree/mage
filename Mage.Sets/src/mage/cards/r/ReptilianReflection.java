package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReptilianReflection extends CardImpl {

    public ReptilianReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever you cycle a card, Reptilian Reflection becomes a 5/4 Dinosaur creature with trample and haste in addition to its other types until end of turn.
        this.addAbility(new CycleControllerTriggeredAbility(new BecomesCreatureSourceEffect(
                new ReptilianReflectionToken(), CardType.ENCHANTMENT, Duration.EndOfTurn), true));
    }

    private ReptilianReflection(final ReptilianReflection card) {
        super(card);
    }

    @Override
    public ReptilianReflection copy() {
        return new ReptilianReflection(this);
    }
}

class ReptilianReflectionToken extends TokenImpl {

    ReptilianReflectionToken() {
        super("", "5/4 Dinosaur creature with trample and haste");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DINOSAUR);
        power = new MageInt(5);
        toughness = new MageInt(4);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private ReptilianReflectionToken(final ReptilianReflectionToken token) {
        super(token);
    }

    public ReptilianReflectionToken copy() {
        return new ReptilianReflectionToken(this);
    }
}
