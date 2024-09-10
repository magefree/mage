package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BucolicRanch extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Mount spell");

    static {
        filter.add(SubType.MOUNT.getPredicate());
    }

    public BucolicRanch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a Mount spell.
        this.addAbility(new ConditionalAnyColorManaAbility(
                new TapSourceCost(), 1,
                new ConditionalSpellManaBuilder(filter), true
        ));

        // {3}, {T}: Look at the top card of your library. If it's a Mount card, you may reveal it and put it into your hand. If you don't put it into your hand, you may put it on the bottom of your library.
        Ability ability = new SimpleActivatedAbility(new BucolicRanchEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BucolicRanch(final BucolicRanch card) {
        super(card);
    }

    @Override
    public BucolicRanch copy() {
        return new BucolicRanch(this);
    }
}

class BucolicRanchEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a Mount card");

    static {
        filter.add(SubType.MOUNT.getPredicate());
    }

    BucolicRanchEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. If it's a Mount card, " +
                "you may reveal it and put it into your hand. If you don't put it " +
                "into your hand, you may put it on the bottom of your library";
    }

    private BucolicRanchEffect(final BucolicRanchEffect effect) {
        super(effect);
    }

    @Override
    public BucolicRanchEffect copy() {
        return new BucolicRanchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("Top card of library", card, game);
        if (filter.match(card, player.getId(), source, game) && player.chooseUse(
                outcome, "Put " + card.getName() + " into your hand?", source, game
        )) {
            player.revealCards(source, new CardsImpl(card), game);
            player.moveCards(card, Zone.HAND, source, game);
        }
        if (Zone.LIBRARY.equals(game.getState().getZone(card.getId())) && player.chooseUse(
                outcome, "Put " + card.getName() + " on the bottom of your library?", source, game
        )) {
            player.putCardsOnBottomOfLibrary(card, game, source, false);
        }
        return true;
    }
}
