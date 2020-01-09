package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnigmaticIncarnation extends CardImpl {

    public EnigmaticIncarnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{U}");

        // At the beginning of your end step, you may sacrifice another enchantment. If you do, search your library for a creature card with converted mana cost equal to 1 plus the sacrificed enchantment's converted mana cost, put that card onto the battlefield, then shuffle your library.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new EnigmaticIncarnationEffect(), TargetController.YOU, false
        ));
    }

    private EnigmaticIncarnation(final EnigmaticIncarnation card) {
        super(card);
    }

    @Override
    public EnigmaticIncarnation copy() {
        return new EnigmaticIncarnation(this);
    }
}

class EnigmaticIncarnationEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledEnchantmentPermanent("another enchantment you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    EnigmaticIncarnationEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another enchantment. If you do, " +
                "search your library for a creature card with converted mana cost " +
                "equal to 1 plus the sacrificed enchantment's converted mana cost, " +
                "put that card onto the battlefield, then shuffle your library.";
    }

    private EnigmaticIncarnationEffect(final EnigmaticIncarnationEffect effect) {
        super(effect);
    }

    @Override
    public EnigmaticIncarnationEffect copy() {
        return new EnigmaticIncarnationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null
                || game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) == 0
                || !player.chooseUse(outcome, "Sacrifice an enchantment?", source, game)) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 1, filter, true);
        player.choose(outcome, target, source.getSourceId(), game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int cmc = permanent.getConvertedManaCost();
        if (!permanent.sacrifice(source.getSourceId(), game)) {
            return false;
        }
        FilterCard filterCard = new FilterCreatureCard("creature card with converted mana cost " + (cmc + 1));
        filterCard.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, cmc + 1));
        return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filterCard)).apply(game, source);
    }
}