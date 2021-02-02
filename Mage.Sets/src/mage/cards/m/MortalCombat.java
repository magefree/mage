package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author daagar
 */
public final class MortalCombat extends CardImpl {

    public MortalCombat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // At the beginning of your upkeep, if twenty or more creature cards are in your graveyard, you win the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false),
                new TwentyGraveyardCreatureCondition(),
                "At the beginning of your upkeep, if twenty or more creature cards are in your graveyard, you win the game."));
    }

    private MortalCombat(final MortalCombat card) {
        super(card);
    }

    @Override
    public MortalCombat copy() {
        return new MortalCombat(this);
    }
}

class TwentyGraveyardCreatureCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) >= 20;
    }
}
