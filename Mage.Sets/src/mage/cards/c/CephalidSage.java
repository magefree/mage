package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class CephalidSage extends CardImpl {

    public CephalidSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.OCTOPUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Threshold - As long as seven or more cards are in your graveyard, Cephalid Sage has "When Cephalid Sage enters the battlefield, draw three cards, then discard two cards."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new EntersBattlefieldTriggeredAbility(
                        new DrawDiscardControllerEffect(3, 2)
                )), ThresholdCondition.instance, "as long as seven or more cards are in your graveyard, " +
                "{this} has \"When {this} enters the battlefield, draw three cards, then discard two cards.\""
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private CephalidSage(final CephalidSage card) {
        super(card);
    }

    @Override
    public CephalidSage copy() {
        return new CephalidSage(this);
    }
}
