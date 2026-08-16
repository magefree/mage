package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author muz
 */
public final class BrawnAmadeusCho extends CardImpl {

    public BrawnAmadeusCho(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Brawn enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Power-up -- {4}{G/U}: Put a +1/+1 counter on Brawn for each card in your hand.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(),
                CardsInControllerHandCount.ANY
            ).setText("Put a +1/+1 counter on {this} for each card in your hand"),
            new ManaCostsImpl<>("{4}{G/U}")
        );
        this.addAbility(ability);
    }

    private BrawnAmadeusCho(final BrawnAmadeusCho card) {
        super(card);
    }

    @Override
    public BrawnAmadeusCho copy() {
        return new BrawnAmadeusCho(this);
    }
}
