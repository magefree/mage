package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EladamriKorvecdal extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("cast creature spells");

    public EladamriKorvecdal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast creature spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayFromTopOfLibraryEffect(filter)));

        // {G}, {T}, Tap two untapped creatures you control: Reveal a card from your hand or the top card of your library. If you reveal a creature card this way, put it onto the battlefield. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new EladamriKorvecdalEffect(), new ManaCostsImpl<>("{G}"), MyTurnCondition.instance
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(
                2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
        )));
        this.addAbility(ability);
    }

    private EladamriKorvecdal(final EladamriKorvecdal card) {
        super(card);
    }

    @Override
    public EladamriKorvecdal copy() {
        return new EladamriKorvecdal(this);
    }
}

class EladamriKorvecdalEffect extends OneShotEffect {

    EladamriKorvecdalEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Reveal a card from your hand or the top card of your library. "
                + "If you reveal a creature card this way, put it onto the battlefield.";
    }

    private EladamriKorvecdalEffect(final EladamriKorvecdalEffect effect) {
        super(effect);
    }

    @Override
    public EladamriKorvecdalEffect copy() {
        return new EladamriKorvecdalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        // This was implemented before ruling was clear.
        // It is assumed for now that:
        // - if you have no card in hand you must reveal top of library.
        // - if you have no library, you must reveal a card from your hand.
        boolean canRevealFromLibrary = player.getLibrary().hasCards();
        boolean canRevealFromHand = !player.getHand().isEmpty();

        if (canRevealFromHand && canRevealFromLibrary) {
            if (player.chooseUse(
                    Outcome.PutCreatureInPlay,
                    "Where do you want to reveal a card from?", "",
                    "from hand", "from top of library",
                    source, game
            )) {
                revealFromHand(player, game, source);
            } else {
                revealFromTop(player, game, source);
            }
            return true;
        } else if (canRevealFromHand) {
            return revealFromHand(player, game, source);
        } else if (canRevealFromLibrary) {
            return revealFromTop(player, game, source);
        } else {
            return false;
        }
    }

    private boolean revealFromTop(Player player, Game game, Ability source) {
        CardsImpl cards = new CardsImpl();
        Card card = player.getLibrary().getFromTop(game);
        MageObject mageObject = source.getSourceObject(game);
        if (card == null || mageObject == null) {
            return false;
        }
        cards.add(card);
        player.revealCards(mageObject.getName() + " (from top of library)", cards, game, false);
        game.informPlayers(player.getLogName() + " reveals " + card.getLogName() + " from top of library" + CardUtil.getSourceLogName(game, source));
        if (card.isCreature(game)) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }

    private boolean revealFromHand(Player player, Game game, Ability source) {
        CardsImpl cards = new CardsImpl();
        Card card = null;
        boolean madeAChoice = false;
        if (player.getHand().size() > 0) {
            Target target = new TargetCardInHand(StaticFilters.FILTER_CARD);
            target.withNotTarget(true);
            if (player.chooseTarget(outcome, target, source, game)) {
                card = game.getCard(target.getFirstTarget());
                madeAChoice = true;
            }
        }
        MageObject mageObject = source.getSourceObject(game);
        if (card == null || mageObject == null) {
            return madeAChoice;
        }
        cards.add(card);
        player.revealCards(mageObject.getName() + " (from hand)", cards, game, false);
        game.informPlayers(player.getLogName() + " reveals " + card.getLogName() + " from hand" + CardUtil.getSourceLogName(game, source));
        if (card.isCreature(game)) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}