package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrborgLhurgoyf extends CardImpl {

    private static final DynamicValue powerValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
    private static final DynamicValue toughnessValue = new AdditiveDynamicValue(powerValue, StaticValue.get(1));

    public UrborgLhurgoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.LHURGOYF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Kicker {U} and/or {B}
        KickerAbility kickerAbility = new KickerAbility("{U}");
        kickerAbility.addKickerCost("{B}");
        this.addAbility(kickerAbility);

        // As Urborg Lhurgoyf enters the battlefield, mill three cards for each time it was kicked.
        this.addAbility(new EntersBattlefieldAbility(
                new MillCardsControllerEffect(MultikickerCount.instance), null,
                "As {this} enters the battlefield, " +
                        "mill three cards for each time it was kicked.", ""
        ));

        // Urborg Lhurgoyf's power is equal to the number of creature cards in your graveyard and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(powerValue, toughnessValue, Duration.EndOfGame, SubLayer.CharacteristicDefining_7a, false)
                        .setText("{this}'s power is equal to the number of creature cards in your graveyard and its toughness is equal to that number plus 1")
        ));
    }

    private UrborgLhurgoyf(final UrborgLhurgoyf card) {
        super(card);
    }

    @Override
    public UrborgLhurgoyf copy() {
        return new UrborgLhurgoyf(this);
    }
}
