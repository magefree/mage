package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.DiscardedByOpponentTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class MangarasBlessing extends CardImpl {

    public MangarasBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // You gain 5 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(5));

        // When a spell or ability an opponent controls causes you to discard Mangara's Blessing,
        // you gain 2 life, and you return Mangara's Blessing from your graveyard to your hand at the beginning of the next end step.
        Ability ability = new DiscardedByOpponentTriggeredAbility(new GainLifeEffect(2), true);

        Effect graveyardReturnNextEndEffect = new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnSourceFromGraveyardToHandEffect()));
        graveyardReturnNextEndEffect.setText(", and you return {this} from your graveyard to your hand at the beginning of the next end step");

        ability.addEffect(graveyardReturnNextEndEffect);
        this.addAbility(ability);
    }

    private MangarasBlessing(final MangarasBlessing card) {
        super(card);
    }

    @Override
    public MangarasBlessing copy() {
        return new MangarasBlessing(this);
    }
}
