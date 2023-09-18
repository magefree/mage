
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class BondsOfMortality extends CardImpl {

    public BondsOfMortality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");

        // When Bonds of Mortality enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // {G}: Creatures your opponents control lose hexproof and indestructible until end of turn.
        Effect effect = new LoseAbilityAllEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES);
        effect.setText("Creatures your opponents control lose hexproof");
        Ability ability = new SimpleActivatedAbility(effect, new ColoredManaCost(ColoredManaSymbol.G));
        effect = new LoseAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES);
        effect.setText("and indestructible until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private BondsOfMortality(final BondsOfMortality card) {
        super(card);
    }

    @Override
    public BondsOfMortality copy() {
        return new BondsOfMortality(this);
    }
}
