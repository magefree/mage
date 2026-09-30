package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author notshauna
 */

public final class NicoMinoruRunaway extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");
    
    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public NicoMinoruRunaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        //Whenever you cast a spell from anywhere other than your hand, Nico Minoru deals 2 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
        new DamagePlayersEffect(2, TargetController.OPPONENT),
        filter, false
        ));

        //{2}{R}, {T}, Discard a card: Exile cards from the top of your library until you exile a nonland card. You may cast that card without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(
            new NicoMinoruRunawayEffect(),
            new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability);
    }
    
    private NicoMinoruRunaway(final NicoMinoruRunaway card) {
        super(card);
    }

    @Override
    public NicoMinoruRunaway copy() {
        return new NicoMinoruRunaway(this);
    }

    class NicoMinoruRunawayEffect extends OneShotEffect {

        NicoMinoruRunawayEffect() {
            super(Outcome.PlayForFree);
            staticText = "Exile cards from the top of your library until you exile a nonland card." +
                    " You may cast that card without paying its mana cost.";
        }

        private NicoMinoruRunawayEffect(final NicoMinoruRunawayEffect effect) {
            super(effect);
        }

        @Override
        public NicoMinoruRunawayEffect copy() {
            return new NicoMinoruRunawayEffect(this);
        }

        private Card getCard(Player player, Cards cards, Game game, Ability source) {
            for (Card card : player.getLibrary().getCards(game)) {
                cards.add(card);
                player.moveCards(card, Zone.EXILED, source, game);
                game.processAction();
                if (!card.isLand(game)) {
                    return card;
                }
            }
            return null;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                return false;
            }
            Cards cards = new CardsImpl();
            Card card = getCard(player, cards, game, source);
            if (card != null) {
                CardUtil.castSpellWithAttributesForFree(player, source, game, card);
            }
            cards.retainZone(Zone.EXILED, game);
            return true;
        }
    }
}