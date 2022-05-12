package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AuraBarbs extends CardImpl {

    public AuraBarbs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");
        this.subtype.add(SubType.ARCANE);


        // Each enchantment deals 2 damage to its controller, then each Aura attached to a creature deals 2 damage to the creature it's attached to.
        this.getSpellAbility().addEffect(new AuraBarbsEffect());
    }

    private AuraBarbs(final AuraBarbs card) {
        super(card);
    }

    @Override
    public AuraBarbs copy() {
        return new AuraBarbs(this);
    }

    private static class AuraBarbsEffect extends OneShotEffect {

        public AuraBarbsEffect() {
            super(Outcome.Detriment);
            staticText = "Each enchantment deals 2 damage to its controller, then each Aura attached to a creature deals 2 damage to the creature it's attached to";
        }

        public AuraBarbsEffect(final AuraBarbsEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {

            FilterPermanent filterEnchantments = new FilterPermanent();
            filterEnchantments.add(CardType.ENCHANTMENT.getPredicate());

            for (Permanent permanent : game.getBattlefield().getActivePermanents(filterEnchantments, source.getControllerId(), source, game)) {
                Player controller = game.getPlayer(permanent.getControllerId());
                if (controller != null) {
                    controller.damage(2, permanent.getId(), source, game);
                    game.informPlayers("2 damage assigned to " + controller.getLogName() + " from " + permanent.getName());
                }
            }

            filterEnchantments.add(SubType.AURA.getPredicate());
            for (Permanent auraEnchantment : game.getBattlefield().getActivePermanents(filterEnchantments, source.getControllerId(), source, game)) {
                if (auraEnchantment.getAttachedTo() != null) {
                    Permanent attachedToCreature = game.getPermanent(auraEnchantment.getAttachedTo());
                    if (attachedToCreature != null && attachedToCreature.isCreature(game)) {
                        attachedToCreature.damage(2, auraEnchantment.getId(), source, game, false, true);
                        game.informPlayers("2 damage assigned to " + attachedToCreature.getName() + " from " + auraEnchantment.getName());
                    }
                }
            }
            return true;
        }

        @Override
        public AuraBarbsEffect copy() {
            return new AuraBarbsEffect(this);
        }

    }
}

