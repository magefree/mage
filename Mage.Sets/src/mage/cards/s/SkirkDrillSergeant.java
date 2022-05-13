
package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SkirkDrillSergeant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("or another Goblin");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public SkirkDrillSergeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Skirk Drill Sergeant or another Goblin dies, you may pay {2}{R}. If you do, reveal the top card of your library. If it's a Goblin permanent card, put it onto the battlefield. Otherwise, put it into your graveyard.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new DoIfCostPaid(new SkirkDrillSergeantEffect(), new ManaCostsImpl("{2}{R}")), false, filter));

    }

    private SkirkDrillSergeant(final SkirkDrillSergeant card) {
        super(card);
    }

    @Override
    public SkirkDrillSergeant copy() {
        return new SkirkDrillSergeant(this);
    }
}

class SkirkDrillSergeantEffect extends OneShotEffect {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Goblin permanent card");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public SkirkDrillSergeantEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal the top card of your library. If it's a Goblin permanent card, put it onto the battlefield. Otherwise, put it into your graveyard";
    }

    public SkirkDrillSergeantEffect(final SkirkDrillSergeantEffect effect) {
        super(effect);
    }

    @Override
    public SkirkDrillSergeantEffect copy() {
        return new SkirkDrillSergeantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }

        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
            if (card != null) {
                if (filter.match(card, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
        }
        return true;
    }
}
