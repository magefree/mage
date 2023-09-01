package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.RatCantBlockToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RatcatcherTrainee extends AdventureCard {

    public RatcatcherTrainee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{1}{R}", "Pest Problem", "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As long as it's your turn, Ratcatcher Trainee has first strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "As long as it's your turn, {this} has first strike."
        )).addHint(MyTurnHint.instance));

        // Pest Problem
        // Create two 1/1 black Rat creature tokens with "This creature can’t block."
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new RatCantBlockToken(), 2));

        this.finalizeAdventure();
    }

    private RatcatcherTrainee(final RatcatcherTrainee card) {
        super(card);
    }

    @Override
    public RatcatcherTrainee copy() {
        return new RatcatcherTrainee(this);
    }
}
