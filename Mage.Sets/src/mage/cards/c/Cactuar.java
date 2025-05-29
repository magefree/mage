package mage.cards.c;

import mage.MageInt;
import mage.abilities.condition.common.SourceEnteredThisTurnCondition;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Cactuar extends CardImpl {

    public Cactuar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your end step, if this creature didn't enter the battlefield this turn, return it to its owner's hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ReturnToHandSourceEffect(true)
                        .setText("return it to its owner's hand")
        ).withInterveningIf(SourceEnteredThisTurnCondition.DIDNT));
    }

    private Cactuar(final Cactuar card) {
        super(card);
    }

    @Override
    public Cactuar copy() {
        return new Cactuar(this);
    }
}
