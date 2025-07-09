package mage.cards.s;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class Serenity extends CardImpl {

    public Serenity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of your upkeep, destroy all artifacts and enchantments. They can't be regenerated.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DestroyAllEffect(
                StaticFilters.FILTER_PERMANENT_ARTIFACTS_AND_ENCHANTMENTS, true
        )));
    }

    private Serenity(final Serenity card) {
        super(card);
    }

    @Override
    public Serenity copy() {
        return new Serenity(this);
    }
}
