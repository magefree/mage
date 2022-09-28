package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetEnchantmentPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author L_J
 */
public final class PowerLeak extends CardImpl {

    public PowerLeak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant enchantment
        TargetPermanent auraTarget = new TargetEnchantmentPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of the upkeep of enchanted enchantment's controller, that player may pay any amount of mana. Power Leak deals 2 damage to that player. Prevent X of that damage, where X is the amount of mana that player paid this way.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PowerLeakEffect(), TargetController.CONTROLLER_ATTACHED_TO, false, true, "At the beginning of the upkeep of enchanted enchantment's controller, "));
    }

    private PowerLeak(final PowerLeak card) {
        super(card);
    }

    @Override
    public PowerLeak copy() {
        return new PowerLeak(this);
    }
}

class PowerLeakEffect extends OneShotEffect {

    public PowerLeakEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player may pay any amount of mana. {this} deals 2 damage to that player. Prevent X of that damage, where X is the amount of mana that player paid this way";
    }

    public PowerLeakEffect(final PowerLeakEffect effect) {
        super(effect);
    }

    @Override
    public PowerLeakEffect copy() {
        return new PowerLeakEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        ManaCosts<ManaCost> cost = new ManaCostsImpl<>("{X}");
        String message = "Pay {X} to prevent X damage from " + permanent.getLogName() + "?";
        int xValue = 0;
        if (player.chooseUse(Outcome.Neutral, message, source, game)) {
            xValue = player.announceXMana(0, Integer.MAX_VALUE, "Choose the amount of mana to pay", game, source);
            cost.add(new GenericManaCost(xValue));
            if (cost.pay(source, game, source, player.getId(), false, null)) {
                game.informPlayers(player.getLogName() + " paid {" + xValue + "} for " + permanent.getLogName());
            } else {
                game.informPlayers(player.getLogName() + " didn't pay {X} for " + permanent.getLogName());
            }
        } else {
            game.informPlayers(player.getLogName() + " didn't pay {X} for " + permanent.getLogName());
        }

        PreventDamageByTargetEffect effect = new PreventDamageByTargetEffect(Duration.OneUse, xValue, false);
        if (xValue != 0 && cost.isPaid()) {
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        player.damage(2, source.getSourceId(), source, game);
        effect.discard();
        return true;
    }
}
