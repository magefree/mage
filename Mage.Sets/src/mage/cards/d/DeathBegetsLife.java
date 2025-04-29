package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author androosss
 */
public final class DeathBegetsLife extends CardImpl {

    public DeathBegetsLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.SORCERY }, "{5}{B}{G}{U}");

        // Destroy all creatures and enchantments. Draw a card for each permanent
        // destroyed this way.
        this.getSpellAbility().addEffect(new DeathBegetsLifeEffect());
    }

    private DeathBegetsLife(final DeathBegetsLife card) {
        super(card);
    }

    @Override
    public DeathBegetsLife copy() {
        return new DeathBegetsLife(this);
    }
}

class DeathBegetsLifeEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("creature or enchantment");
    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    DeathBegetsLifeEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures and enchantments. Draw a card for each permanent destroyed this way";
    }

    private DeathBegetsLifeEffect(final DeathBegetsLifeEffect effect) {
        super(effect);
    }

    @Override
    public DeathBegetsLifeEffect copy() {
        return new DeathBegetsLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int destroyedPermanent = 0;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter,
                    controller.getId(), game)) {
                if (permanent.destroy(source, game)) {
                    destroyedPermanent++;
                }
            }
            if (destroyedPermanent > 0) {
                game.processAction();
                controller.drawCards(destroyedPermanent, source, game);
            }
            return true;
        }
        return false;
    }
}
