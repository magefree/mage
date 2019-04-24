
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;


/**
 *
 * @author emerald000
 */
public final class ShieldOfTheAvatar extends CardImpl {

    public ShieldOfTheAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // If a source would deal damage to equipped creature, prevent X of that damage, where X is the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ShieldOfTheAvatarPreventionEffect()));
        
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public ShieldOfTheAvatar(final ShieldOfTheAvatar card) {
        super(card);
    }

    @Override
    public ShieldOfTheAvatar copy() {
        return new ShieldOfTheAvatar(this);
    }
}

class ShieldOfTheAvatarPreventionEffect extends PreventionEffectImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control");
    
    ShieldOfTheAvatarPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "If a source would deal damage to equipped creature, prevent X of that damage, where X is the number of creatures you control.";
    }

    ShieldOfTheAvatarPreventionEffect(final ShieldOfTheAvatarPreventionEffect effect) {
        super(effect);
    }

    @Override
    public ShieldOfTheAvatarPreventionEffect copy() {
        return new ShieldOfTheAvatarPreventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        boolean result = false;
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            int numberOfCreaturesControlled = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
            int toPrevent = Math.min(numberOfCreaturesControlled, event.getAmount());
            GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, equipment.getAttachedTo(), source.getSourceId(), source.getControllerId(), toPrevent, false);
            if (!game.replaceEvent(preventEvent)) {
                if (event.getAmount() >= toPrevent) {
                    event.setAmount(event.getAmount() - toPrevent);
                }
                else {
                    event.setAmount(0);
                    result = true;
                }
                if (toPrevent > 0) {
                    game.informPlayers(new StringBuilder("Shield of the Avatar ").append("prevented ").append(toPrevent).append(" damage to ").append(game.getPermanent(equipment.getAttachedTo()).getName()).toString());
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE,
                            equipment.getAttachedTo(), source.getSourceId(), source.getControllerId(), toPrevent));
                }
            }
        }
        return result;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null
                    && event.getTargetId().equals(equipment.getAttachedTo())) {
                return true;
            }
        }
        return false;
    }
}
