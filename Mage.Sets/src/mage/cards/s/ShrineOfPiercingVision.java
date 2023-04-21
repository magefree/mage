
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public final class ShrineOfPiercingVision extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public ShrineOfPiercingVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of your upkeep or whenever you cast a blue spell, put a charge counter on Shrine of Piercing Vision.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                new BeginningOfUpkeepTriggeredAbility(null, TargetController.YOU, false),
                new SpellCastControllerTriggeredAbility(null, filter, false)));

        // {tap}, Sacrifice Shrine of Piercing Vision: Look at the top X cards of your library, where X is the number of charge counters on Shrine of Piercing Vision.
        // Put one of those cards into your hand and the rest on the bottom of your library in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShrineOfPiercingVisionEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ShrineOfPiercingVision(final ShrineOfPiercingVision card) {
        super(card);
    }

    @Override
    public ShrineOfPiercingVision copy() {
        return new ShrineOfPiercingVision(this);
    }
}

class ShrineOfPiercingVisionEffect extends OneShotEffect {

    public ShrineOfPiercingVisionEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top X cards of your library, where X is the number of charge counters on {this}. Put one of those cards into your hand and the rest on the bottom of your library in any order";
    }

    public ShrineOfPiercingVisionEffect(final ShrineOfPiercingVisionEffect effect) {
        super(effect);
    }

    @Override
    public ShrineOfPiercingVisionEffect copy() {
        return new ShrineOfPiercingVisionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (player == null || permanent == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, permanent.getCounters(game).getCount(CounterType.CHARGE)));
        if (!cards.isEmpty()) {
            player.lookAtCards(source, null, cards, game);
            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into your hand"));
            if (player.choose(Outcome.DrawCard, cards, target, source, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    player.moveCards(card, Zone.HAND, source, game);
                }
            }
            player.putCardsOnBottomOfLibrary(cards, game, source, true);
        }
        return true;
    }
}
