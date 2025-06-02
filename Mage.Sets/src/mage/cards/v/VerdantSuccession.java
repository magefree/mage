package mage.cards.v;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SharesNamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VerdantSuccession extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a green nontoken creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(TokenPredicate.FALSE);
    }

    public VerdantSuccession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // Whenever a green nontoken creature dies, that creature's controller may search their library for a card with the same name as that creature and put it onto the battlefield. If that player does, they shuffle their library.
        this.addAbility(new DiesCreatureTriggeredAbility(new VerdantSuccessionEffect(), false, filter));
    }

    private VerdantSuccession(final VerdantSuccession card) {
        super(card);
    }

    @Override
    public VerdantSuccession copy() {
        return new VerdantSuccession(this);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}

class VerdantSuccessionEffect extends OneShotEffect {

    VerdantSuccessionEffect() {
        super(Outcome.Benefit);
        staticText = "that creature's controller may search their library for a card " +
                "with the same name as that creature, put it onto the battlefield, then shuffle.";
    }

    private VerdantSuccessionEffect(final VerdantSuccessionEffect effect) {
        super(effect);
    }

    @Override
    public VerdantSuccessionEffect copy() {
        return new VerdantSuccessionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("creatureDied");
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null || !player.chooseUse(
                Outcome.PutCreatureInPlay, "Search for a card with the same name?", source, game
        )) {
            return false;
        }
        FilterCard filter = new FilterCard("card with the same name");
        filter.add(new SharesNamePredicate(permanent));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
