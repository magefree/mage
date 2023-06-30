package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KioraSovereignOfTheDeep extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Kraken, Leviathan, Octopus, or Serpent spell");

    static {
        filter.add(Predicates.or(
                SubType.KRAKEN.getPredicate(),
                SubType.LEVIATHAN.getPredicate(),
                SubType.OCTOPUS.getPredicate(),
                SubType.SERPENT.getPredicate()
        ));
    }

    public KioraSovereignOfTheDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}"), false));

        // Whenever you cast a Kraken, Leviathan, Octopus, or Serpent spell from your hand, look at the top X cards of your library, where X is that spell's mana value. You may cast a spell with mana value less than X from among them without paying its mana cost. Put the rest on the bottom of your library in a random order.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new KioraSovereignOfTheDeepEffect(), filter, false, Zone.HAND
        ));
    }

    private KioraSovereignOfTheDeep(final KioraSovereignOfTheDeep card) {
        super(card);
    }

    @Override
    public KioraSovereignOfTheDeep copy() {
        return new KioraSovereignOfTheDeep(this);
    }
}

class KioraSovereignOfTheDeepEffect extends OneShotEffect {

    KioraSovereignOfTheDeepEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top X cards of your library, where X is that spell's mana value. " +
                "You may cast a spell with mana value less than X from among them without " +
                "paying its mana cost. Put the rest on the bottom of your library in a random order";
    }

    private KioraSovereignOfTheDeepEffect(final KioraSovereignOfTheDeepEffect effect) {
        super(effect);
    }

    @Override
    public KioraSovereignOfTheDeepEffect copy() {
        return new KioraSovereignOfTheDeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (player == null || spell == null || spell.getManaValue() < 1) {
            return false;
        }
        int xValue = spell.getManaValue();
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, xValue));
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue));
        CardUtil.castSpellWithAttributesForFree(player, source, game, cards, filter);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
