
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class YisanTheWandererBard extends CardImpl {

    public YisanTheWandererBard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.BARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{G}, {T}, Put a verse counter on Yisan, the Wanderer Bard: Search your library for a creature card with converted mana cost equal to the number of verse counters on Yisan, put it onto the battlefield, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new YisanTheWandererBardEffect(), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PutCountersSourceCost(CounterType.VERSE.createInstance()));
        this.addAbility(ability);
    }

    private YisanTheWandererBard(final YisanTheWandererBard card) {
        super(card);
    }

    @Override
    public YisanTheWandererBard copy() {
        return new YisanTheWandererBard(this);
    }
}

class YisanTheWandererBardEffect extends OneShotEffect {

    public YisanTheWandererBardEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for a creature card with mana value equal to the number of verse counters on {this}, put it onto the battlefield, then shuffle";
    }

    public YisanTheWandererBardEffect(final YisanTheWandererBardEffect effect) {
        super(effect);
    }

    @Override
    public YisanTheWandererBardEffect copy() {
        return new YisanTheWandererBardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && controller != null) {
            int newConvertedCost = sourcePermanent.getCounters(game).getCount(CounterType.VERSE);
            FilterCard filter = new FilterCard("creature card with mana value " + newConvertedCost);
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, newConvertedCost));
            filter.add(CardType.CREATURE.getPredicate());
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, source, game)) {
                Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
