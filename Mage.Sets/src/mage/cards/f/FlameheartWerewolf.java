
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.BlocksOrBecomesBlockedTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class FlameheartWerewolf extends CardImpl {

    public FlameheartWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        // Whenever Flameheart Werewolf blocks or becomes blocked by a creature, Flameheart Werewolf deals 2 damage to that creature.
        this.addAbility(new BlocksOrBecomesBlockedTriggeredAbility(new DamageTargetEffect(2, true, "that creature"),
                StaticFilters.FILTER_PERMANENT_CREATURE, false, null, true));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Flameheart Werewolf.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public FlameheartWerewolf(final FlameheartWerewolf card) {
        super(card);
    }

    @Override
    public FlameheartWerewolf copy() {
        return new FlameheartWerewolf(this);
    }
}
