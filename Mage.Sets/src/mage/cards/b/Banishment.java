package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author freaisdead
 */
public final class Banishment extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Banishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Banishment enters the battlefield, exile target nonland permanent an opponent controls and all other nonland permanents your opponents control with the same name as that permanent until Banishment leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BanishmentEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private Banishment(final Banishment card) {
        super(card);
    }

    @Override
    public Banishment copy() {
        return new Banishment(this);
    }
}


class BanishmentEffect extends OneShotEffect {

    BanishmentEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target nonland permanent an opponent controls " +
                "and all other nonland permanents your opponents control " +
                "with the same name as that permanent until {this} leaves the battlefield";
    }

    private BanishmentEffect(final BanishmentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent targeted = game.getPermanent(source.getFirstTarget());

        if (permanent == null || controller == null || targeted == null) {
            return false;
        }

        FilterPermanent filter = new FilterNonlandPermanent();
        filter.add(new NamePredicate(targeted.getName()));

        Set<Card> toExile = game.getBattlefield().getAllActivePermanents(filter, game)
                .stream().filter(p -> controller.hasOpponent(p.getControllerId(),game))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (!toExile.isEmpty()) {
            controller.moveCardsToExile(toExile, source, game, true, CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
            new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()).apply(game, source);
        }
        return true;
    }

    @Override
    public BanishmentEffect copy() {
        return new BanishmentEffect(this);
    }

}