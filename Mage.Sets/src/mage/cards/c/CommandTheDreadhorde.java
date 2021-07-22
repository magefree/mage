package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommandTheDreadhorde extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature and/or planeswalker cards in graveyards");

    static {
        filter.add(Predicates.or(
                CardType.PLANESWALKER.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public CommandTheDreadhorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Choose any number of target creature and/or planeswalker cards in graveyards. Command the Dreadhorde deals damage to you equal to the total converted mana cost of those cards. Put them onto the battlefield under your control.
        this.getSpellAbility().addEffect(new CommandTheDreadhordeEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, Integer.MAX_VALUE, filter));
    }

    private CommandTheDreadhorde(final CommandTheDreadhorde card) {
        super(card);
    }

    @Override
    public CommandTheDreadhorde copy() {
        return new CommandTheDreadhorde(this);
    }
}

class CommandTheDreadhordeEffect extends OneShotEffect {

    CommandTheDreadhordeEffect() {
        super(Outcome.Benefit);
        staticText = "Choose any number of target creature and/or planeswalker cards in graveyards. " +
                "{this} deals damage to you equal to the total mana value of those cards. " +
                "Put them onto the battlefield under your control.";
    }

    private CommandTheDreadhordeEffect(final CommandTheDreadhordeEffect effect) {
        super(effect);
    }

    @Override
    public CommandTheDreadhordeEffect copy() {
        return new CommandTheDreadhordeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(source.getTargets().get(0).getTargets());
        int damage = cards.getCards(game).stream().mapToInt(Card::getManaValue).sum();
        player.damage(damage, source.getSourceId(), source, game);
        return player.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}