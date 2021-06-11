package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResurgentBelief extends CardImpl {

    public ResurgentBelief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setWhite(true);

        // Suspend 2—{1}{W}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl<>("{1}{W}"), this));

        // Return all enchantment cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ResurgentBeliefEffect());
    }

    private ResurgentBelief(final ResurgentBelief card) {
        super(card);
    }

    @Override
    public ResurgentBelief copy() {
        return new ResurgentBelief(this);
    }
}

class ResurgentBeliefEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterEnchantmentCard();

    ResurgentBeliefEffect() {
        super(Outcome.Benefit);
        staticText = "return all enchantment cards from your graveyard to the battlefield";
    }

    private ResurgentBeliefEffect(final ResurgentBeliefEffect effect) {
        super(effect);
    }

    @Override
    public ResurgentBeliefEffect copy() {
        return new ResurgentBeliefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.moveCards(
                player.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game
        );
    }
}
