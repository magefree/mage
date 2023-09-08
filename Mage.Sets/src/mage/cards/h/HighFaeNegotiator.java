package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.BargainAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HighFaeNegotiator extends CardImpl {

    public HighFaeNegotiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Bargain
        this.addAbility(new BargainAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When High Fae Negotiator enters the battlefield, if it was bargained, each opponent loses 3 life and you gain 3 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new LoseLifeOpponentsEffect(3)),
                BargainedCondition.instance, "When {this} enters the battlefield, " +
                "if it was bargained, each opponent loses 3 life and you gain 3 life."
        );
        ability.addEffect(new GainLifeEffect(3));
        this.addAbility(ability);
    }

    private HighFaeNegotiator(final HighFaeNegotiator card) {
        super(card);
    }

    @Override
    public HighFaeNegotiator copy() {
        return new HighFaeNegotiator(this);
    }
}
