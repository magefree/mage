
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author Loki
 */
public final class SilverpeltWerewolf extends CardImpl {

    public SilverpeltWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},null);
        this.subtype.add(SubType.WEREWOLF);
        this.color.setGreen(true);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        this.transformable = true;
        this.nightCard = true;

        // Whenever Silverpelt Werewolf deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Silverpelt Werewolf.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public SilverpeltWerewolf(final SilverpeltWerewolf card) {
        super(card);
    }

    @Override
    public SilverpeltWerewolf copy() {
        return new SilverpeltWerewolf(this);
    }
}
