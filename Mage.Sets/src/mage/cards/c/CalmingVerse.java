
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class CalmingVerse extends CardImpl {

    public CalmingVerse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Destroy all enchantments you don't control. Then, if you control an untapped land, destroy all enchantments you control.
        this.getSpellAbility().addEffect(new CalmingVerseEffect());

    }

    private CalmingVerse(final CalmingVerse card) {
        super(card);
    }

    @Override
    public CalmingVerse copy() {
        return new CalmingVerse(this);
    }
}

class CalmingVerseEffect extends OneShotEffect {

    private static final FilterPermanent untappedLandFilter = new FilterPermanent("If you control an untapped land");

    static {
        untappedLandFilter.add(CardType.LAND.getPredicate());
        untappedLandFilter.add(TappedPredicate.UNTAPPED);
    }

    private static final FilterEnchantmentPermanent opponentEnchantmentsFilter = new FilterEnchantmentPermanent("enchantments you don't control");

    static {
        opponentEnchantmentsFilter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final FilterControlledEnchantmentPermanent controlledEnchantmentsFilter = new FilterControlledEnchantmentPermanent("enchantments you control");

    public CalmingVerseEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy all enchantments you don't control. Then if you control an untapped land, destroy all enchantments you control";
    }

    public CalmingVerseEffect(final CalmingVerseEffect effect) {
        super(effect);
    }

    @Override
    public CalmingVerseEffect copy() {
        return new CalmingVerseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Destroy all other enchantments
        for (Permanent permanent : game.getBattlefield().getActivePermanents(opponentEnchantmentsFilter, source.getControllerId(), source, game)) {
            permanent.destroy(source, game, false);
        }

        // Then if you control an untapped land, destroy all own enchantments
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {

            if (game.getState().getBattlefield().countAll(untappedLandFilter, controller.getId(), game) > 0) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(controlledEnchantmentsFilter, source.getControllerId(), source, game)) {
                    permanent.destroy(source, game, false);
                }
            }

        }
        return true;
    }
}
