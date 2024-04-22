package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaskingCapybara extends CardImpl {

    public BaskingCapybara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.CAPYBARA);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Descend 4 -- Basking Capybara gets +3/+0 as long as there are four or more permanent cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield), DescendCondition.FOUR,
                "{this} gets +3/+0 as long as there are four or more permanent cards in your graveyard"
        )).setAbilityWord(AbilityWord.DESCEND_4).addHint(DescendCondition.getHint()));
    }

    private BaskingCapybara(final BaskingCapybara card) {
        super(card);
    }

    @Override
    public BaskingCapybara copy() {
        return new BaskingCapybara(this);
    }
}
