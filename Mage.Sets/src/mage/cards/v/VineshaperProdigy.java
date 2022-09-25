package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VineshaperProdigy extends CardImpl {

    public VineshaperProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{U}
        this.addAbility(new KickerAbility("{1}{U}"));

        // When Vineshaper Prodigy enters the battlefield, if it was kicked, look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new LookLibraryAndPickControllerEffect(
                                3, 1,
                                LookLibraryControllerEffect.PutCards.HAND,
                                LookLibraryControllerEffect.PutCards.BOTTOM_ANY
                        )), KickedCondition.ONCE, "When {this} enters the battlefield, " +
                "if it was kicked, look at the top three cards of your library. " +
                "Put one of them into your hand and the rest on the bottom of your library in any order."
        ));
    }

    private VineshaperProdigy(final VineshaperProdigy card) {
        super(card);
    }

    @Override
    public VineshaperProdigy copy() {
        return new VineshaperProdigy(this);
    }
}
