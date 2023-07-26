package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.common.TapVariableTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.WhiteAstartesWarriorToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BelisariusCawl extends CardImpl {

    private static final FilterControlledPermanent artifactFilter =
        new FilterControlledPermanent("untapped artifacts you control");

    static {
        artifactFilter.add(TappedPredicate.UNTAPPED);
        artifactFilter.add(CardType.ARTIFACT.getPredicate());
    }

    private static final FilterControlledPermanent creatureFilter =
        new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        creatureFilter.add(TappedPredicate.UNTAPPED);
    }

    public BelisariusCawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Ultima Founding -- {T}, Tap two untapped artifacts you control: Create a 2/2 white Astartes Warrior creature token with vigilance.
        Ability ultima = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new WhiteAstartesWarriorToken()), new TapSourceCost());
        ultima.addCost(new TapTargetCost(new TargetControlledPermanent(2, 2, artifactFilter, false)));
        ultima.withFlavorWord("Ultima Founding");
        this.addAbility(ultima);

        // Master of Machines -- {T}, Tap X untapped creatures you control: Look at the top X cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        Ability master = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BelisariusCawlEffect(), new TapSourceCost());
        master.addCost(new TapVariableTargetCost(creatureFilter));
        master.withFlavorWord("Master of Machines");
        this.addAbility(master);
    }

    private BelisariusCawl(final BelisariusCawl card) {
        super(card);
    }

    @Override
    public BelisariusCawl copy() {
        return new BelisariusCawl(this);
    }
}

class BelisariusCawlEffect extends OneShotEffect {

    public BelisariusCawlEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top X cards of your library. You may reveal an artifact card " +
            "from among them and put it into your hand. Put the rest on the bottom of your library in a random order.";
    }

    public BelisariusCawlEffect(final BelisariusCawlEffect effect) {
        super(effect);
    }

    @Override
    public BelisariusCawlEffect copy() {
        return new BelisariusCawlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int amount = (GetXValue.instance).calculate(game, source, this);

        if (amount > 0) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, amount));
            if (!cards.isEmpty()) {
                TargetCard target = new TargetCard(0, 1, Zone.LIBRARY, StaticFilters.FILTER_CARD_ARTIFACT_AN);
                target.setNotTarget(true);
                controller.chooseTarget(Outcome.Benefit, cards, target, source, game);

                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.revealCards(source, new CardsImpl(card), game);
                    controller.moveCardToHandWithInfo(card, source, game, true);
                }

                controller.putCardsOnBottomOfLibrary(cards, game, source, false);
            }
        }

        return true;
    }
}
