
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class AuntiesSnitch extends CardImpl {

    public AuntiesSnitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Auntie's Snitch can't block.
        this.addAbility(new CantBlockAbility());
        // Prowl {1}{B}
        this.addAbility(new ProwlAbility(this, "{1}{B}"));
        // Whenever a Goblin or Rogue you control deals combat damage to a player, if Auntie's Snitch is in your graveyard, you may return Auntie's Snitch to your hand.
        this.addAbility(new AuntiesSnitchTriggeredAbility());
    }

    private AuntiesSnitch(final AuntiesSnitch card) {
        super(card);
    }

    @Override
    public AuntiesSnitch copy() {
        return new AuntiesSnitch(this);
    }
}

class AuntiesSnitchTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Goblin or Rogue you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.or(SubType.GOBLIN.getPredicate(), SubType.ROGUE.getPredicate()));
    }

    public AuntiesSnitchTriggeredAbility() {
        super(Zone.GRAVEYARD, new ReturnToHandSourceEffect(), true);
    }

    public AuntiesSnitchTriggeredAbility(final AuntiesSnitchTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AuntiesSnitchTriggeredAbility copy() {
        return new AuntiesSnitchTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
        Permanent p = game.getPermanent(event.getSourceId());
        return damageEvent.isCombatDamage() && filter.match(p, getControllerId(), this, game);
    }

    @Override
    public String getRule() {
        return "Whenever a Goblin or Rogue you control deals combat damage to a player, if {this} is in your graveyard, you may return {this} to your hand.";
    }
}
