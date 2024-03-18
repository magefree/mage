package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PsychicFrog extends CardImpl {

    public PsychicFrog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.FROG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Psychic Frog deals combat damage to a player or planeswalker, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));

        // Discard a card: Put a +1/+1 counter on Psychic Frog.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new DiscardCardCost()
        ));

        // Exile three cards from your graveyard: Psychic Frog gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new ExileFromGraveCost(new TargetCardInYourGraveyard(3))
        ));
    }

    private PsychicFrog(final PsychicFrog card) {
        super(card);
    }

    @Override
    public PsychicFrog copy() {
        return new PsychicFrog(this);
    }
}
