package mage.game.command.emblems;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.ManaPaidSourceWatcher;

/**
 *
 * @author weirddan455
 */
public class ChandraDressedToKillEmblem extends Emblem {

    private static final FilterSpell filter = new FilterSpell("a red spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    // Whenever you cast a red spell, this emblem deals X damage to any target, where X is the amount of mana spent to cast that spell.
    public ChandraDressedToKillEmblem() {
        this.setName("Emblem Chandra");
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.COMMAND, new ChandraDressedToKillEmblemEffect(), filter, false, true);
        ability.addTarget(new TargetAnyTarget());
        this.getAbilities().add(ability);

        this.setExpansionSetCodeForImage("VOW");
    }
}

class ChandraDressedToKillEmblemEffect extends OneShotEffect {

    public ChandraDressedToKillEmblemEffect() {
        super(Outcome.Damage);
        staticText = "this emblem deals X damage to any target, where X is the amount of mana spent to cast that spell";
    }

    private ChandraDressedToKillEmblemEffect(final ChandraDressedToKillEmblemEffect effect) {
        super(effect);
    }

    @Override
    public ChandraDressedToKillEmblemEffect copy() {
        return new ChandraDressedToKillEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (controller == null || spell == null) {
            return false;
        }
        int manaPaid = ManaPaidSourceWatcher.getTotalPaid(spell.getId(), game);
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(manaPaid, source, game);
            return true;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(manaPaid, source, game);
            return true;
        }
        return false;
    }
}
