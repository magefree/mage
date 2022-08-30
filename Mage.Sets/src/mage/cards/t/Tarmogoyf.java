package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Tarmogoyf extends CardImpl {

    private static final DynamicValue powerValue = CardTypesInGraveyardCount.ALL;
    private static final DynamicValue toughnessValue = new AdditiveDynamicValue(powerValue, StaticValue.get(1));

    public Tarmogoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.LHURGOYF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Tarmogoyf's power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(powerValue, toughnessValue, Duration.EndOfGame, SubLayer.CharacteristicDefining_7a, false)
                        .setText("{this}'s power is equal to the number of creature cards in all graveyards and its toughness is equal to that number plus 1")
        ));
    }

    private Tarmogoyf(final Tarmogoyf card) {
        super(card);
    }

    @Override
    public Tarmogoyf copy() {
        return new Tarmogoyf(this);
    }
}
