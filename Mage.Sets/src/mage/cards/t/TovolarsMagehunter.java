
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
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
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public final class TovolarsMagehunter extends CardImpl {

    public TovolarsMagehunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        // Whenever an opponent casts a spell, Tovolar's Magehunter deals 2 damage to that player.
        this.addAbility(new TovolarsMagehunterTriggeredAbility());
        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Tovolar's Magehunter.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability,
                TwoOrMoreSpellsWereCastLastTurnCondition.instance,
                TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public TovolarsMagehunter(final TovolarsMagehunter card) {
        super(card);
    }

    @Override
    public TovolarsMagehunter copy() {
        return new TovolarsMagehunter(this);
    }
}

class TovolarsMagehunterTriggeredAbility extends TriggeredAbilityImpl {

    public TovolarsMagehunterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
    }

    public TovolarsMagehunterTriggeredAbility(final TovolarsMagehunterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TovolarsMagehunterTriggeredAbility copy() {
        return new TovolarsMagehunterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell, {this} deals 2 damage to that player.";
    }
}
