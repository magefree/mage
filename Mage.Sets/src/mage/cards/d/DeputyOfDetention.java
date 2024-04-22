package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeputyOfDetention extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public DeputyOfDetention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Deputy of Detention enters the battlefield, exile target nonland permanent an opponent controls and all other nonland permanents that player controls with the same name as that permanent until Deputy of Detention leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DeputyOfDetentionExileEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private DeputyOfDetention(final DeputyOfDetention card) {
        super(card);
    }

    @Override
    public DeputyOfDetention copy() {
        return new DeputyOfDetention(this);
    }
}

class DeputyOfDetentionExileEffect extends OneShotEffect {

    DeputyOfDetentionExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target nonland permanent an opponent controls " +
                "and all other nonland permanents that player controls " +
                "with the same name as that permanent until {this} leaves the battlefield";
    }

    private DeputyOfDetentionExileEffect(final DeputyOfDetentionExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent targeted = game.getPermanent(source.getFirstTarget());

        if (permanent == null || controller == null || targeted == null) {
            return false;
        }

        FilterPermanent filter = new FilterNonlandPermanent();
        filter.add(new ControllerIdPredicate(targeted.getControllerId()));
        filter.add(new NamePredicate(targeted.getName()));

        Set<Card> toExile = new LinkedHashSet<>();
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, controller.getId(), game)) {
            toExile.add(creature);
        }

        if (!toExile.isEmpty()) {
            controller.moveCardsToExile(toExile, source, game, true, CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
            game.addDelayedTriggeredAbility(new OnLeaveReturnExiledAbility(), source);
        }
        return true;
    }

    @Override
    public DeputyOfDetentionExileEffect copy() {
        return new DeputyOfDetentionExileEffect(this);
    }

}
