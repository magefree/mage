
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class ChampionOfRhonas extends CardImpl {

    public ChampionOfRhonas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You may exert Champion of Rhonas as it attacks. When you do, you may put a creature card from your hand onto the battlefield.
        BecomesExertSourceTriggeredAbility ability = new BecomesExertSourceTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A));
        this.addAbility(new ExertAbility(ability));
    }

    private ChampionOfRhonas(final ChampionOfRhonas card) {
        super(card);
    }

    @Override
    public ChampionOfRhonas copy() {
        return new ChampionOfRhonas(this);
    }
}
