
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class LegacyOfTheBeloved extends CardImpl {

    public LegacyOfTheBeloved(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // As an additional cost to cast Legacy of the Beloved, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        // Search you library for up to two creatures cards that each have a lower converted mana cost that sacrificied creature's converted mana cost, reveal them and put them onto the battlefield, then shuffle you library.
        this.getSpellAbility().addEffect(new LegacyOfTheBelovedEffect());
    }

    public LegacyOfTheBeloved(final LegacyOfTheBeloved card) {
        super(card);
    }

    @Override
    public LegacyOfTheBeloved copy() {
        return new LegacyOfTheBeloved(this);
    }
}

class LegacyOfTheBelovedEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("up to two creatures cards that each have a lower converted mana cost that sacrificied creature's converted mana cost");

    public LegacyOfTheBelovedEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search you library for up to two creatures cards that each have a lower converted mana cost that sacrificied creature's converted mana cost, reveal them and put them onto the battlefield, then shuffle you library";
    }

    public LegacyOfTheBelovedEffect(final LegacyOfTheBelovedEffect effect) {
        super(effect);
    }

    @Override
    public LegacyOfTheBelovedEffect copy() {
        return new LegacyOfTheBelovedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard != null) {
            for (Cost cost : source.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    Permanent p = (Permanent) game.getLastKnownInformation(((SacrificeTargetCost) cost).getPermanents().get(0).getId(), Zone.BATTLEFIELD);
                    if (p != null) {
                        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, p.getConvertedManaCost()));
                        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, filter);
                        Player player = game.getPlayer(source.getControllerId());
                        if (player != null && player.searchLibrary(target, source, game)) {
                            player.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, false, false, false, null);
                            player.shuffleLibrary(source, game);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
