package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Enslave extends CardImpl {

    public Enslave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect()));

        // At the beginning of your upkeep, enchanted creature deals 1 damage to its owner.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new EnslaveEffect(), TargetController.YOU, false, false));
    }

    private Enslave(final Enslave card) {
        super(card);
    }

    @Override
    public Enslave copy() {
        return new Enslave(this);
    }

}

class EnslaveEffect extends OneShotEffect {

    EnslaveEffect() {
        super(Outcome.Damage);
        staticText = "enchanted creature deals 1 damage to its owner";
    }

    EnslaveEffect(final EnslaveEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null) {
            Permanent attached = game.getPermanentOrLKIBattlefield(sourcePermanent.getAttachedTo());
            if (attached != null) {
                Player owner = game.getPlayer(attached.getOwnerId());
                if (owner != null) {
                    owner.damage(1, attached.getId(), source, game);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public EnslaveEffect copy() {
        return new EnslaveEffect(this);
    }

}
