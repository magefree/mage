package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class CombatCelebrant extends CardImpl {

    public CombatCelebrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // If Combat Celebrant hasn't been exerted this turn, you may exert it as it attacks. When you do, untap all other creatures you control and after this phase, there is an additional combat phase.
        BecomesExertSourceTriggeredAbility ability = new BecomesExertSourceTriggeredAbility(new UntapAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURES, null, false));
        ability.addEffect(new AdditionalCombatPhaseEffect("and after this phase, there is an additional combat phase"));
        this.addAbility(new ExertAbility(ability, true));
    }

    private CombatCelebrant(final CombatCelebrant card) {
        super(card);
    }

    @Override
    public CombatCelebrant copy() {
        return new CombatCelebrant(this);
    }
}
