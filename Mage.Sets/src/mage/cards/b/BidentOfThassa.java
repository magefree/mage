
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class BidentOfThassa extends CardImpl {

    public BidentOfThassa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.ARTIFACT},"{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);


        // Whenever a creature you control deals combat damage to a player, you may draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                true, SetTargetPointer.NONE, true
        ));
        // {1}{U}, {T}: Creatures your opponents control attack this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AttacksIfAbleAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES,Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BidentOfThassa(final BidentOfThassa card) {
        super(card);
    }

    @Override
    public BidentOfThassa copy() {
        return new BidentOfThassa(this);
    }
}
