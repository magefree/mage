
package mage.cards.l;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class LatullasOrders extends CardImpl {

    public LatullasOrders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Protect));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted creature deals combat damage to defending player, you may destroy target artifact that player controls.
        this.addAbility(new LatullasOrdersTriggeredAbility());
    }

    private LatullasOrders(final LatullasOrders card) {
        super(card);
    }

    @Override
    public LatullasOrders copy() {
        return new LatullasOrders(this);
    }
}

class LatullasOrdersTriggeredAbility extends TriggeredAbilityImpl {

    public LatullasOrdersTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
    }

    public LatullasOrdersTriggeredAbility(final LatullasOrdersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LatullasOrdersTriggeredAbility copy() {
        return new LatullasOrdersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(this.getSourceId());
        if (event.getSourceId().equals(enchantment.getAttachedTo()) && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                FilterPermanent filter = new FilterPermanent("an artifact controlled by " + player.getLogName());
                filter.add(CardType.ARTIFACT.getPredicate());
                filter.add(new ControllerIdPredicate(event.getTargetId()));

                this.getTargets().clear();
                this.addTarget(new TargetPermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature deals combat damage to defending player, you may destroy target artifact that player controls.";
    }
}
