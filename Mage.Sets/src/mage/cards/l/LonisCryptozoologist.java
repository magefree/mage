package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class LonisCryptozoologist extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another nontoken creature");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.CLUE, "Clues");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public LonisCryptozoologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever another nontoken creature enters the battlefield under your control, investigate.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new InvestigateEffect(), filter, false, null, true));

        // {T}, Sacrifice X Clues: Target opponent reveals the top X cards of their library.
        // You may put a nonland permanent card with mana value X or less from among them onto the battlefield under your control.
        // That player puts the rest on the bottom of their library in a random order.
        Ability ability = new SimpleActivatedAbility(new LonisCryptozoologistEffect(), new TapSourceCost());
        ability.addCost(new SacrificeXTargetCost(filter2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private LonisCryptozoologist(final LonisCryptozoologist card) {
        super(card);
    }

    @Override
    public LonisCryptozoologist copy() {
        return new LonisCryptozoologist(this);
    }
}

class LonisCryptozoologistEffect extends OneShotEffect {

    public LonisCryptozoologistEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Target opponent reveals the top X cards of their library."
                + " You may put a nonland permanent card with mana value X or less from among them onto the battlefield under your control."
                + " That player puts the rest on the bottom of their library in a random order";
    }

    private LonisCryptozoologistEffect(final LonisCryptozoologistEffect effect) {
        super(effect);
    }

    @Override
    public LonisCryptozoologistEffect copy() {
        return new LonisCryptozoologistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && opponent != null) {
            int xValue = GetXValue.instance.calculate(game, source, this);
            Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, xValue));
            opponent.revealCards(source, cards, game);
            if (controller.chooseUse(outcome, "Put a nonland permanent card with mana value " + xValue
                    + " or less from among revealed cards onto the battlefield under your control?", source, game)) {
                FilterPermanentCard filter = new FilterPermanentCard("nonland permanent card with mana value " + xValue + " or less");
                filter.add(Predicates.not(CardType.LAND.getPredicate()));
                filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
                TargetCard target = new TargetCard(Zone.LIBRARY, filter);
                if (controller.choose(outcome, cards, target, source, game)) {
                    Card selectedCard = game.getCard(target.getFirstTarget());
                    if (selectedCard != null) {
                        cards.remove(selectedCard);
                        controller.moveCards(selectedCard, Zone.BATTLEFIELD, source, game);
                    }
                }
            }
            opponent.putCardsOnBottomOfLibrary(cards, game, source, false);
            return true;
        }
        return false;
    }
}
