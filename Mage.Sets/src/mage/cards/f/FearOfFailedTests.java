package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearOfFailedTests extends CardImpl {

    public FearOfFailedTests(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // Whenever Fear of Failed Tests deals combat damage to a player, draw that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY), false, true
        ));
    }

    private FearOfFailedTests(final FearOfFailedTests card) {
        super(card);
    }

    @Override
    public FearOfFailedTests copy() {
        return new FearOfFailedTests(this);
    }
}
