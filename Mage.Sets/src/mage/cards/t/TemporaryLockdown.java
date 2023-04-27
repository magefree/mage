package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemporaryLockdown extends CardImpl {

    public TemporaryLockdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // When Temporary Lockdown enters the battlefield, exile each nonland permanent with mana value 2 or less until Temporary Lockdown leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TemporaryLockdownEffect());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private TemporaryLockdown(final TemporaryLockdown card) {
        super(card);
    }

    @Override
    public TemporaryLockdown copy() {
        return new TemporaryLockdown(this);
    }
}

class TemporaryLockdownEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    TemporaryLockdownEffect() {
        super(Outcome.Benefit);
        staticText = "exile each nonland permanent with mana value 2 or less until {this} leaves the battlefield";
    }

    private TemporaryLockdownEffect(final TemporaryLockdownEffect effect) {
        super(effect);
    }

    @Override
    public TemporaryLockdownEffect copy() {
        return new TemporaryLockdownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || source.getSourcePermanentIfItStillExists(game) == null) {
            return false;
        }
        Cards cards = new CardsImpl(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game));
        return !cards.isEmpty() && player.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
    }
}
