
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public final class PriestOfTheWakeningSun extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dinosaur card");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
    }

    public PriestOfTheWakeningSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, you may reveal a Dinosaur card from your hand. If you do, you gain 2 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new PriestOfTheWakeningSunEffect(), TargetController.YOU, true);
        this.addAbility(ability);

        // {3}{W}{W}, Sacrifice Priest of the Wakening Sun: Search your library for a Dinosaur card, reveal it, put it into your hand, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(target), true), new ManaCostsImpl<>("{3}{W}{W}"));
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);
    }

    private PriestOfTheWakeningSun(final PriestOfTheWakeningSun card) {
        super(card);
    }

    @Override
    public PriestOfTheWakeningSun copy() {
        return new PriestOfTheWakeningSun(this);
    }
}

class PriestOfTheWakeningSunEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a Dinosaur card to reveal");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
    }

    PriestOfTheWakeningSunEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal a Dinosaur card from your hand. If you do, you gain 2 life";
    }

    PriestOfTheWakeningSunEffect(final PriestOfTheWakeningSunEffect effect) {
        super(effect);
    }

    @Override
    public PriestOfTheWakeningSunEffect copy() {
        return new PriestOfTheWakeningSunEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);

        if (controller != null && sourceObject != null) {
            if (controller.getHand().count(filter, source.getControllerId(), source, game) > 0) {
                if (controller.chooseUse(outcome, "Reveal a Dinosaur card?", source, game)) {
                    TargetCardInHand target = new TargetCardInHand(0, 1, filter);
                    if (controller.chooseTarget(outcome, target, source, game) && !target.getTargets().isEmpty()) {
                        Cards cards = new CardsImpl(target.getTargets());
                        controller.revealCards(sourceObject.getIdName(), cards, game);
                        controller.gainLife(2, game, source);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
