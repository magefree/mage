package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.ManaUtil;

import java.util.UUID;

/**
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
        Ability ability = new EnchantAbility(auraTarget);
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
        if (controllerOfEnchantedCreature != null && controllerOfEnchantedCreature.canRespond()) {
            int manaPaid = ManaUtil.playerPaysXGenericMana(false, "Errant Minion", controllerOfEnchantedCreature, source, game);
            if (manaPaid > 0) {
                PreventDamageToTargetEffect effect = new PreventDamageToTargetEffect(Duration.OneUse, manaPaid);
                effect.setTargetPointer(new FixedTarget(controllerOfEnchantedCreature.getId()));
                game.addEffect(effect, source);
            }
            DamageTargetEffect effect2 = new DamageTargetEffect(2);
            effect2.setTargetPointer(new FixedTarget(controllerOfEnchantedCreature.getId()));
            effect2.apply(game, source);
            return true;
        }
        return false;
    }
}
