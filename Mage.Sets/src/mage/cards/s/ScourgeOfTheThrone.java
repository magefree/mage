
package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksFirstTimeTriggeredAbility;
import mage.abilities.condition.common.SourceAttackingPlayerWithMostLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.keyword.DethroneAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ScourgeOfTheThrone extends CardImpl {

    public ScourgeOfTheThrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Dethrone (Whenever this creature attacks the player with the most life or tied for most life, put a +1/+1 counter on it.)
        this.addAbility(new DethroneAbility());
        // Whenever Scourge of the Throne attacks for the first time each turn, if it's attacking the player with the most life or tied for most life, untap all attacking creatures. After this phase, there is an additional combat phase.
        TriggeredAbility ability = new AttacksFirstTimeTriggeredAbility(
                new UntapAllControllerEffect(
                        StaticFilters.FILTER_ATTACKING_CREATURES,
                        "untap all attacking creatures"
                ), false
        );
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability,
                SourceAttackingPlayerWithMostLifeCondition.instance,
                "Whenever {this} attacks for the first time each turn, "
                        + "if it's attacking the player with the most life or tied for most life, "
                        + "untap all attacking creatures. After this phase, "
                        + "there is an additional combat phase."
        ));
    }

    private ScourgeOfTheThrone(final ScourgeOfTheThrone card) {
        super(card);
    }

    @Override
    public ScourgeOfTheThrone copy() {
        return new ScourgeOfTheThrone(this);
    }
}