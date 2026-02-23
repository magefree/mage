package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SidequestCardCollection extends TransformingDoubleFacedCard {

    private static final Condition condition = new CardsInControllerGraveyardCondition(8);

    public SidequestCardCollection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{3}{U}",
                "Magicked Card",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "U"
        );

        // Sidequest: Card Collection
        // When this enchantment enters, draw three cards, then discard two cards.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(3, 2)));

        // At the beginning of your end step, if eight or more cards are in your graveyard, transform this enchantment.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(new TransformSourceEffect()).withInterveningIf(condition));

        // Magicked Card
        this.getRightHalfCard().setPT(4, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Crew 1
        this.getRightHalfCard().addAbility(new CrewAbility(1));
    }

    private SidequestCardCollection(final SidequestCardCollection card) {
        super(card);
    }

    @Override
    public SidequestCardCollection copy() {
        return new SidequestCardCollection(this);
    }
}
