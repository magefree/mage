package mage.cards.m;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author daagar
 */
public final class MortalCombat extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(20, StaticFilters.FILTER_CARD_CREATURES);

    public MortalCombat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // At the beginning of your upkeep, if twenty or more creature cards are in your graveyard, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect()).withInterveningIf(condition));
    }

    private MortalCombat(final MortalCombat card) {
        super(card);
    }

    @Override
    public MortalCombat copy() {
        return new MortalCombat(this);
    }
}
