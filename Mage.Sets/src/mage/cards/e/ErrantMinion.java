package mage.cards.e;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class ErrantMinion extends CardImpl {

    public ErrantMinion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of the upkeep of enchanted creature's controller, that player may pay any amount of mana. Errant Minion deals 2 damage to that player. Prevent X of that damage, where X is the amount of mana that player paid this way.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD,
                new ErrantMinionEffect(),
                TargetController.CONTROLLER_ATTACHED_TO,
                false));

    }

    private ErrantMinion(final ErrantMinion card) {
        super(card);
    }

    @Override
    public ErrantMinion copy() {
        return new ErrantMinion(this);
    }
}

class ErrantMinionEffect extends OneShotEffect {

    public ErrantMinionEffect() {
        super(Outcome.Damage);
        this.staticText = "that player may pay any amount of mana. Errant Minion deals 2 damage to that player. Prevent X of that damage, where X is the amount of mana that player paid this way";
    }

    public ErrantMinionEffect(final ErrantMinionEffect effect) {
        super(effect);
    }

    @Override
    public ErrantMinionEffect copy() {
        return new ErrantMinionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent errantMinion = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (errantMinion == null) {
            return false;
        }
        Permanent enchantedCreature = game.getPermanentOrLKIBattlefield(errantMinion.getAttachedTo());
        if (enchantedCreature == null) {
            return false;
        }
        Player controllerOfEnchantedCreature = game.getPlayer(enchantedCreature.getControllerId());
        if (controllerOfEnchantedCreature != null) {
            int manaPaid = playerPaysXGenericMana(controllerOfEnchantedCreature, source, game);
            PreventDamageToTargetEffect effect = new PreventDamageToTargetEffect(Duration.OneUse, manaPaid);
            effect.setTargetPointer(new FixedTarget(controllerOfEnchantedCreature.getId()));
            game.addEffect(effect, source);
            DamageTargetEffect effect2 = new DamageTargetEffect(2);
            effect2.setTargetPointer(new FixedTarget(controllerOfEnchantedCreature.getId()));
            effect2.apply(game, source);
            return true;
        }
        return false;
    }

    protected static int playerPaysXGenericMana(Player player, Ability source, Game game) {
        int xValue = 0;
        boolean payed = false;
        while (!payed) {
            xValue = player.announceXMana(0, Integer.MAX_VALUE, "How much mana will you pay?", game, source);
            if (xValue > 0) {
                Cost cost = new GenericManaCost(xValue);
                payed = cost.pay(source, game, source.getSourceId(), player.getId(), false, null);
            } else {
                payed = true;
            }
        }
        game.informPlayers(player.getLogName() + " pays {" + xValue + '}');
        return xValue;
    }

}
