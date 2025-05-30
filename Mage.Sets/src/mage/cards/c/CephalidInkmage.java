package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CephalidInkmage extends CardImpl {

    public CephalidInkmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.OCTOPUS);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, surveil 3.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(3)));

        // Threshold -- This creature can't be blocked as long as there are seven or more cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), ThresholdCondition.instance, "this creature " +
                "can't be blocked as long as there are seven or more cards in your graveyard"
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private CephalidInkmage(final CephalidInkmage card) {
        super(card);
    }

    @Override
    public CephalidInkmage copy() {
        return new CephalidInkmage(this);
    }
}
