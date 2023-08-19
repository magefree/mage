package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public final class MerfolkWindrobber extends CardImpl {

    public MerfolkWindrobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Merfolk Windrobber deals combat damage to a player, that player mills a card.
        this.addAbility(new MerfolkWindrobberTriggeredAbility());

        // Sacrifice Merfolk Windrobber: Draw a card. Activate this ability only if an opponent has eight or more cards in their graveyard.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                new SacrificeSourceCost(), CardsInOpponentGraveyardCondition.EIGHT
        ).addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));
    }

    private MerfolkWindrobber(final MerfolkWindrobber card) {
        super(card);
    }

    @Override
    public MerfolkWindrobber copy() {
        return new MerfolkWindrobber(this);
    }
}

class MerfolkWindrobberTriggeredAbility extends TriggeredAbilityImpl {

    public MerfolkWindrobberTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MillCardsTargetEffect(1));
    }

    public MerfolkWindrobberTriggeredAbility(final MerfolkWindrobberTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MerfolkWindrobberTriggeredAbility copy() {
        return new MerfolkWindrobberTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, that player mills a card.";
    }
}
