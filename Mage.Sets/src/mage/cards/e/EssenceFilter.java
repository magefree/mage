
package mage.cards.e;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2 & L_J
 */
public final class EssenceFilter extends CardImpl {

    public EssenceFilter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{G}");

        // Destroy all enchantments or all nonwhite enchantments.
        this.getSpellAbility().addEffect(new EssenceFilterEffect());
    }

    private EssenceFilter(final EssenceFilter card) {
        super(card);
    }

    @Override
    public EssenceFilter copy() {
        return new EssenceFilter(this);
    }
}

class EssenceFilterEffect extends OneShotEffect {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("nonwhite enchantments");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }

    public EssenceFilterEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy all enchantments or all nonwhite enchantments";
    }

    public EssenceFilterEffect(final EssenceFilterEffect effect) {
        super(effect);
    }

    @Override
    public EssenceFilterEffect copy() {
        return new EssenceFilterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(outcome, "Destroy all enchantments? (otherwise all nonwhite enchantments are destroyed)", source, game)) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterEnchantmentPermanent(), controller.getId(), source, game)) {
                    permanent.destroy(source, game, false);
                }
            } else {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, controller.getId(), source, game)) {
                    permanent.destroy(source, game, false);
                }
            }
            return true;
        }
        return false;
    }
}
