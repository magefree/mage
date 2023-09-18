package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.common.OpponentsLostLifeHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArrogantOutlaw extends CardImpl {

    public ArrogantOutlaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Arrogant Outlaw enters the battlefield, if an opponent lost life this turn, each opponent loses 2 life and you gain 2 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new LoseLifeOpponentsEffect(2), false
                ), OpponentsLostLifeCondition.instance, "When {this} enters the battlefield, " +
                "if an opponent lost life this turn, each opponent loses 2 life and you gain 2 life."
        );
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability.addHint(OpponentsLostLifeHint.instance));
    }

    private ArrogantOutlaw(final ArrogantOutlaw card) {
        super(card);
    }

    @Override
    public ArrogantOutlaw copy() {
        return new ArrogantOutlaw(this);
    }
}
