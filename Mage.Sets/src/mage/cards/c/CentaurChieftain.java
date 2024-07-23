package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class CentaurChieftain extends CardImpl {

    public CentaurChieftain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Threshold - As long as seven or more cards are in your graveyard, Centaur Chieftain has "When Centaur Chieftain enters the battlefield, creatures you control get +1/+1 and gain trample until end of turn."
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+1")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain trample until end of turn"));
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(ability), ThresholdCondition.instance, "As long as seven " +
                "or more cards are in your graveyard, {this} has \"When {this} enters the battlefield, " +
                "creatures you control get +1/+1 and gain trample until end of turn.\""
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private CentaurChieftain(final CentaurChieftain card) {
        super(card);
    }

    @Override
    public CentaurChieftain copy() {
        return new CentaurChieftain(this);
    }
}
