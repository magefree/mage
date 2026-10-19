package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

import java.util.Collections;
import java.util.UUID;

public final class JuggernautPeddler extends CardImpl {

    public JuggernautPeddler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(VigilanceAbility.getInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(new JuggernautPeddlerEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private JuggernautPeddler(final JuggernautPeddler card) {
        super(card);
    }

    @Override
    public JuggernautPeddler copy() {
        return new JuggernautPeddler(this);
    }
}

class JuggernautPeddlerEffect extends OneShotEffect {

    JuggernautPeddlerEffect() {
        super(Outcome.Discard);
        staticText = "target player reveals all nonland cards in their hand. You may choose one of those cards. "
                + "If you do, that player exiles it and conjures a card named Juggernaut into their hand";
    }

    private JuggernautPeddlerEffect(final JuggernautPeddlerEffect effect) {
        super(effect);
    }

    @Override
    public JuggernautPeddlerEffect copy() {
        return new JuggernautPeddlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getHand().getCards(StaticFilters.FILTER_CARD_NON_LAND, game));
        player.revealCards(source, cards, game);
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCard(0, 1, Zone.HAND, StaticFilters.FILTER_CARD_NON_LAND);
        controller.choose(Outcome.Exile, cards, target, source, game);
        Card card = cards.get(target.getFirstTarget(), game);
        if (card == null) {
            return true;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        Card juggernaut = ConjureCardEffect.createCard("Juggernaut");
        if (juggernaut == null) {
            return false;
        }
        game.loadCards(Collections.singleton(juggernaut), player.getId());
        return player.moveCards(juggernaut, Zone.HAND, source, game);
    }
}
