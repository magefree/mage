package mage.cards.t;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DiscardsACardOpponentTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TourachDreadCantor extends CardImpl {

    public TourachDreadCantor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Kicker {B}{B}
        this.addAbility(new KickerAbility("{B}{B}"));

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));

        // Whenever an opponent discards a card, put a +1/+1 counter on Tourach, Dread Cantor.
        this.addAbility(new DiscardsACardOpponentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));

        // When Tourach enters the battelfield, if it was kicked, target opponent discards two cards at random.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(2, true)),
                KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, " +
                "target opponent discards two cards at random."
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TourachDreadCantor(final TourachDreadCantor card) {
        super(card);
    }

    @Override
    public TourachDreadCantor copy() {
        return new TourachDreadCantor(this);
    }
}
