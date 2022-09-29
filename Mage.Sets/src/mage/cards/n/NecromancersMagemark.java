
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Markedagain
 */
public final class NecromancersMagemark extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control that are enchanted");

    static {
        filter.add(EnchantedPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public NecromancersMagemark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Creatures you control that are enchanted get +1/+1.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false));
        this.addAbility(ability);

        // If a creature you control that's enchanted would die, return it to its owner's hand instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NecromancersMagemarkEffect()));
    }

    private NecromancersMagemark(final NecromancersMagemark card) {
        super(card);
    }

    @Override
    public NecromancersMagemark copy() {
        return new NecromancersMagemark(this);
    }
}

class NecromancersMagemarkEffect extends ReplacementEffectImpl {

    public NecromancersMagemarkEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature you control that's enchanted would die, return it to its owner's hand instead";
    }

    public NecromancersMagemarkEffect(final NecromancersMagemarkEffect effect) {
        super(effect);
    }

    @Override
    public NecromancersMagemarkEffect copy() {
        return new NecromancersMagemarkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent != null) {
                controller.moveCards(permanent, Zone.HAND, source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent != null && permanent.isControlledBy(source.getControllerId())) {
                for (UUID attachmentId : permanent.getAttachments()) {
                    Permanent attachment = game.getPermanentOrLKIBattlefield(attachmentId);
                    if (attachment != null && attachment.hasSubtype(SubType.AURA, game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
