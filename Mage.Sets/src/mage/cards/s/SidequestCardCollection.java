package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SidequestCardCollection extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(8);

    public SidequestCardCollection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.secondSideCardClazz = mage.cards.m.MagickedCard.class;

        // When this enchantment enters, draw three cards, then discard two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(3, 2)));

        // At the beginning of your end step, if eight or more cards are in your graveyard, transform this enchantment.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new TransformSourceEffect()).withInterveningIf(condition));
    }

    private SidequestCardCollection(final SidequestCardCollection card) {
        super(card);
    }

    @Override
    public SidequestCardCollection copy() {
        return new SidequestCardCollection(this);
    }
}
