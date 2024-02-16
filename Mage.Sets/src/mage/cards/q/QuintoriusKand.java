package mage.cards.q;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.Spirit32Token;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuintoriusKand extends CardImpl {

    public QuintoriusKand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.QUINTORIUS);
        this.setStartingLoyalty(4);

        // Whenever you cast a spell from exile, Quintorius Kand deals 2 damage to each opponent and you gain 2 life.
        Ability ability = new SpellCastControllerTriggeredAbility(
                Zone.BATTLEFIELD, new DamagePlayersEffect(2, TargetController.OPPONENT),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.NONE, Zone.EXILED
        );
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);

        // +1: Create a 3/2 red and white Spirit creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new Spirit32Token()), 1));

        // -3: Discover 4.
        this.addAbility(new LoyaltyAbility(new DiscoverEffect(4), -3));

        // -6: Exile any number of target cards from your graveyard. Add {R} for each card exiled this way. You may play those cards this turn.
        ability = new LoyaltyAbility(new QuintoriusKandEffect(), -6);
        ability.addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE));
        this.addAbility(ability);
    }

    private QuintoriusKand(final QuintoriusKand card) {
        super(card);
    }

    @Override
    public QuintoriusKand copy() {
        return new QuintoriusKand(this);
    }
}

class QuintoriusKandEffect extends OneShotEffect {

    QuintoriusKandEffect() {
        super(Outcome.Benefit);
        staticText = "exile any number of target cards from your graveyard. " +
                "Add {R} for each card exiled this way. You may play those cards this turn";
    }

    private QuintoriusKandEffect(final QuintoriusKandEffect effect) {
        super(effect);
    }

    @Override
    public QuintoriusKandEffect copy() {
        return new QuintoriusKandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (player == null || cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        player.getManaPool().addMana(Mana.RedMana(cards.size()), game, source);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
        }
        return true;
    }
}

