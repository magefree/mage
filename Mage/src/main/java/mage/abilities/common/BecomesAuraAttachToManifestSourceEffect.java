
package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesAuraSourceEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX2
 */
public class BecomesAuraAttachToManifestSourceEffect extends OneShotEffect {

    public BecomesAuraAttachToManifestSourceEffect() {
        super(Outcome.Benefit);
        this.staticText = "it becomes an Aura with enchant creature. Manifest the top card of your library and attach {this} to it";
    }

    protected BecomesAuraAttachToManifestSourceEffect(final BecomesAuraAttachToManifestSourceEffect effect) {
        super(effect);
    }

    @Override
    public BecomesAuraAttachToManifestSourceEffect copy() {
        return new BecomesAuraAttachToManifestSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (controller != null && enchantment != null) {
            // manifest top card
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                new ManifestEffect(1).apply(game, source);
                Permanent enchantedCreature = game.getPermanent(card.getId());
                if (enchantedCreature != null) {
                    enchantedCreature.addAttachment(enchantment.getId(), source, game);
                    FilterCreaturePermanent filter = new FilterCreaturePermanent();
                    Target target = new TargetCreaturePermanent(filter);
                    target.addTarget(enchantedCreature.getId(), source, game);
                    game.addEffect(new BecomesAuraSourceEffect(target), source);
                }
            }
            return true;
        }
        return false;
    }
}
