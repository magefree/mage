package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.MoloidToken;

/**
 *
 * @author muz
 */
public final class ReturnOfTheMoleMan extends CardImpl {

    private static final DynamicValue xValue =
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENTS);

    public ReturnOfTheMoleMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Landfall -- Whenever a land you control enters, you may mill two cards.
        this.addAbility(new LandfallAbility(new MillCardsControllerEffect(2), true));

        // {5}{G}, Sacrifice this enchantment: Create X 1/1 green Minion creature tokens named Moloid, where X is the number of permanent cards in your graveyard. The tokens have "Whenever this token attacks, you may mill a card." Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new CreateTokenEffect(new MoloidToken(), xValue)
                .setText("Create X 1/1 green Minion creature tokens named Moloid, where X is the number of permanent cards in your graveyard. " +
                        "The tokens have \"Whenever this token attacks, you may mill a card.\""),
            new ManaCostsImpl<>("{5}{G}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability.addHint(DescendCondition.getHint()));
    }

    private ReturnOfTheMoleMan(final ReturnOfTheMoleMan card) {
        super(card);
    }

    @Override
    public ReturnOfTheMoleMan copy() {
        return new ReturnOfTheMoleMan(this);
    }
}
