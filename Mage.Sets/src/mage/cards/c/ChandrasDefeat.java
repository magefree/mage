
package mage.cards.c;

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
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author spjspj
 */
public final class ChandrasDefeat extends CardImpl {

    private final static FilterPermanent filter = new FilterPermanent("creature or planeswalker");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.PLANESWALKER)));
    }

    public ChandrasDefeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Chandra's Defeat deals 5 damage to target red creature or red planeswalker. If it was a Chandra planeswalker, you may discard a card. If you do, draw a card.
        this.getSpellAbility().addEffect(new ChandrasDefeatEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public ChandrasDefeat(final ChandrasDefeat card) {
        super(card);
    }

    @Override
    public ChandrasDefeat copy() {
        return new ChandrasDefeat(this);
    }
}

class ChandrasDefeatEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new SubtypePredicate(SubType.CHANDRA));
    }

    public ChandrasDefeatEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 5 damage to target red creature or red planeswalker. If it was a Chandra planeswalker, you may discard a card. If you do, draw a card.";
    }

    public ChandrasDefeatEffect(final ChandrasDefeatEffect effect) {
        super(effect);
    }

    @Override
    public ChandrasDefeatEffect copy() {
        return new ChandrasDefeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());

        if (permanent != null) {
            permanent.damage(5, source.getSourceId(), game, false, true);

            // If it was a Chandra planeswalker, you may discard a card. If you do, draw a card
            if (filter.match(permanent, game) && controller != null
                    && controller.chooseUse(outcome, "Discard a card and draw a card?", source, game)) {
                controller.discard(1, false, source, game);
                controller.drawCards(1, game);
            }
            return true;
        }
        return false;
    }
}
