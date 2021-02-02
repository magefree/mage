package mage.cards.k;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KumenasAwakening extends CardImpl {

    public KumenasAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // Ascend (If you control ten or more permenants, you get the city's blessing for the rest of the game.)
        this.addAbility(new AscendAbility());

        // At the beginning of your upkeep, each player draws a card. If you have the city's blessing, instead only you draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1), new DrawCardAllEffect(1), CitysBlessingCondition.instance,
                        "each player draws a card. If you have the city's blessing, instead only you draw a card"),
                TargetController.YOU, false)
                .addHint(CitysBlessingHint.instance));
    }

    private KumenasAwakening(final KumenasAwakening card) {
        super(card);
    }

    @Override
    public KumenasAwakening copy() {
        return new KumenasAwakening(this);
    }
}
