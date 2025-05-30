package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuagFeast extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature, planeswalker, or Vehicle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public QuagFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose target creature, planeswalker, or Vehicle. Mill two cards, then destroy the chosen permanent if its mana value is less than or equal to the number of cards in your graveyard.
        this.getSpellAbility().addEffect(new QuagFeastEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private QuagFeast(final QuagFeast card) {
        super(card);
    }

    @Override
    public QuagFeast copy() {
        return new QuagFeast(this);
    }
}

class QuagFeastEffect extends OneShotEffect {

    QuagFeastEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature, planeswalker, or Vehicle. Mill two cards, then destroy the " +
                "chosen permanent if its mana value is less than or equal to the number of cards in your graveyard";
    }

    private QuagFeastEffect(final QuagFeastEffect effect) {
        super(effect);
    }

    @Override
    public QuagFeastEffect copy() {
        return new QuagFeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(2, source, game);
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && permanent.getManaValue() <= player.getGraveyard().size()) {
            permanent.destroy(source, game);
        }
        return true;
    }
}
