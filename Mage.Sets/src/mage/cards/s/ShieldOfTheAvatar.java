package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;


/**
 * @author emerald000
 */
public final class ShieldOfTheAvatar extends CardImpl {

    public ShieldOfTheAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // If a source would deal damage to equipped creature, prevent X of that damage, where X is the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ShieldOfTheAvatarPreventionEffect())
                .addHint(CreaturesYouControlHint.instance));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private ShieldOfTheAvatar(final ShieldOfTheAvatar card) {
        super(card);
    }

    @Override
    public ShieldOfTheAvatar copy() {
        return new ShieldOfTheAvatar(this);
    }
}

class ShieldOfTheAvatarPreventionEffect extends PreventionEffectImpl {

    ShieldOfTheAvatarPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "If a source would deal damage to equipped creature, prevent X of that damage, where X is the number of creatures you control.";
    }

    private ShieldOfTheAvatarPreventionEffect(final ShieldOfTheAvatarPreventionEffect effect) {
        super(effect);
    }

    @Override
    public ShieldOfTheAvatarPreventionEffect copy() {
        return new ShieldOfTheAvatarPreventionEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        boolean result = false;
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            int numberOfCreaturesControlled = CreaturesYouControlCount.instance.calculate(game, source, this);
            int toPrevent = Math.min(numberOfCreaturesControlled, event.getAmount());
            GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), toPrevent, ((DamageEvent) event).isCombatDamage());
            if (!game.replaceEvent(preventEvent)) {
                if (event.getAmount() >= toPrevent) {
                    event.setAmount(event.getAmount() - toPrevent);
                } else {
                    event.setAmount(0);
                    result = true;
                }
                if (toPrevent > 0) {
                    game.informPlayers("Shield of the Avatar " + "prevented " + toPrevent + " damage to " + game.getPermanent(equipment.getAttachedTo()).getName());
                    game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), toPrevent));
                }
            }
        }
        return result;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            return equipment != null && equipment.getAttachedTo() != null
                    && event.getTargetId().equals(equipment.getAttachedTo());
        }
        return false;
    }
}
