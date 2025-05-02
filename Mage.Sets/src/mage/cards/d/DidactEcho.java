package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DidactEcho extends CardImpl {

    public DidactEcho(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Didact Echo enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Descend 4 -- Didact Echo has flying as long as there are four or more permanent cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance()), DescendCondition.FOUR,
                "{this} has flying as long as there are four or more permanent cards in your graveyard"
        )).setAbilityWord(AbilityWord.DESCEND_4).addHint(DescendCondition.getHint()));
    }

    private DidactEcho(final DidactEcho card) {
        super(card);
    }

    @Override
    public DidactEcho copy() {
        return new DidactEcho(this);
    }
}
