package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class TishanaVoiceOfThunder extends CardImpl {

    public TishanaVoiceOfThunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Tishana, Voice of Thunder's power and toughness are each equal to the number of cards in your hand.
        DynamicValue xValue = CardsInControllerHandCount.instance;
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue, Duration.EndOfGame)));

        // You have no maximum hand size.
        Effect effect = new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // When Tishana enters the battlefield, draw a card for each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE))));
    }

    private TishanaVoiceOfThunder(final TishanaVoiceOfThunder card) {
        super(card);
    }

    @Override
    public TishanaVoiceOfThunder copy() {
        return new TishanaVoiceOfThunder(this);
    }
}
