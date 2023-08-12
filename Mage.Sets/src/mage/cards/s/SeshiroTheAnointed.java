

package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SeshiroTheAnointed extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Snake creatures");

    static {
        filter.add(SubType.SNAKE.getPredicate());
    }

    public SeshiroTheAnointed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        
        // Other Snake creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter, true)));
        // Whenever a Snake you control deals combat damage to a player, you may draw a card.
        this.addAbility(new SeshiroTheAnointedAbility());
    }

    private SeshiroTheAnointed(final SeshiroTheAnointed card) {
        super(card);
    }

    @Override
    public SeshiroTheAnointed copy() {
        return new SeshiroTheAnointed(this);
    }

}

class SeshiroTheAnointedAbility extends TriggeredAbilityImpl {

    public SeshiroTheAnointedAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    public SeshiroTheAnointedAbility(final SeshiroTheAnointedAbility ability) {
        super(ability);
    }

    @Override
    public SeshiroTheAnointedAbility copy() {
        return new SeshiroTheAnointedAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
        Permanent p = game.getPermanent(event.getSourceId());
        if (damageEvent.isCombatDamage() && p != null && p.hasSubtype(SubType.SNAKE, game) && p.isControlledBy(controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Snake you control deals combat damage to a player, you may draw a card.";
    }
}