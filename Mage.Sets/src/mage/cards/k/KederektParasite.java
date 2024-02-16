
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class KederektParasite extends CardImpl {

    public KederektParasite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an opponent draws a card, if you control a red permanent, you may have Kederekt Parasite deal 1 damage to that player.
        this.addAbility(new KederektParasiteTriggeredAbility());
    }

    private KederektParasite(final KederektParasite card) {
        super(card);
    }

    @Override
    public KederektParasite copy() {
        return new KederektParasite(this);
    }
}

class KederektParasiteTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    KederektParasiteTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1, true, "opponent"), true);
    }

    private KederektParasiteTriggeredAbility(final KederektParasiteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KederektParasiteTriggeredAbility copy() {
        return new KederektParasiteTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 0).apply(game, this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.getControllerId()).contains(event.getPlayerId())) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent draws a card, if you control a red permanent, you may have {this} deal 1 damage to that player.";
    }
}
