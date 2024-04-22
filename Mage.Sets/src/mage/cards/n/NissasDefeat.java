
package mage.cards.n;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class NissasDefeat extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Forest, green enchantment, or green planeswalker");

    static {
        filter.add(Predicates.or(SubType.FOREST.getPredicate(),
            (Predicates.and(new ColorPredicate(ObjectColor.GREEN), CardType.ENCHANTMENT.getPredicate())),
            (Predicates.and(new ColorPredicate(ObjectColor.GREEN), CardType.PLANESWALKER.getPredicate()))));
    }

    public NissasDefeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Destroy target Forest, green enchantment, or green planeswalker. If that permanent was a Nissa planeswalker, draw a card.
        this.getSpellAbility().addEffect(new NissasDefeatEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private NissasDefeat(final NissasDefeat card) {
        super(card);
    }

    @Override
    public NissasDefeat copy() {
        return new NissasDefeat(this);
    }
}

class NissasDefeatEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.NISSA.getPredicate());
    }

    public NissasDefeatEffect() {
        super(Outcome.Damage);
        this.staticText = "Destroy target Forest, green enchantment, or green planeswalker. If that permanent was a Nissa planeswalker, draw a card.";
    }

    private NissasDefeatEffect(final NissasDefeatEffect effect) {
        super(effect);
    }

    @Override
    public NissasDefeatEffect copy() {
        return new NissasDefeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());

        if (permanent != null) {
            permanent.destroy(source, game, false);

            // If it was a Nissa planeswalker, draw a card
            if (filter.match(permanent, game) && controller != null) {
                controller.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }
}
