
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class OngoingInvestigation extends CardImpl {

    public OngoingInvestigation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever one or more creatures you control deal combat damage to a player, investigate.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(new InvestigateEffect()));

        // {1}{G}, Exile a creature card from your graveyard: Investigate. You gain 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new InvestigateEffect(), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterCreatureCard("a creature card from your graveyard"))));
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);
    }

    private OngoingInvestigation(final OngoingInvestigation card) {
        super(card);
    }

    @Override
    public OngoingInvestigation copy() {
        return new OngoingInvestigation(this);
    }
}
