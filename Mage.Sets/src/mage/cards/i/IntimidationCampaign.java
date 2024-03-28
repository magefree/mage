package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntimidationCampaign extends CardImpl {

    public IntimidationCampaign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{B}");

        // When Intimidation Campaign enters the battlefield, each opponent loses 1 life, you gain 1 life, and you draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeOpponentsEffect(1));
        ability.addEffect(new GainLifeEffect(1).concatBy(","));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", and you"));
        this.addAbility(ability);

        // Whenever you commit a crime, you may return Intimidation Campaign to its owner's hand.
        this.addAbility(new CommittedCrimeTriggeredAbility(
                new ReturnToHandSourceEffect(true), true
        ));
    }

    private IntimidationCampaign(final IntimidationCampaign card) {
        super(card);
    }

    @Override
    public IntimidationCampaign copy() {
        return new IntimidationCampaign(this);
    }
}
