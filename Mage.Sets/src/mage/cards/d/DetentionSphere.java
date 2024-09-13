package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class DetentionSphere extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent not named Detention Sphere");

    static {
        filter.add(Predicates.not(new NamePredicate("Detention Sphere")));
    }

    public DetentionSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");

        // When Detention Sphere enters the battlefield, you may exile
        // target nonland permanent not named Detention Sphere and all
        // other permanents with the same name as that permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DetentionSphereEntersEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // When Detention Sphere leaves the battlefield, return the exiled
        // cards to the battlefield under their owner's control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false));
    }

    private DetentionSphere(final DetentionSphere card) {
        super(card);
    }

    @Override
    public DetentionSphere copy() {
        return new DetentionSphere(this);
    }
}

class DetentionSphereEntersEffect extends OneShotEffect {

    DetentionSphereEntersEffect() {
        super(Outcome.Exile);
        staticText = "exile target nonland permanent not named Detention Sphere " +
                "and all other permanents with the same name as that permanent";
    }

    private DetentionSphereEntersEffect(final DetentionSphereEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        Set<Card> permanents = game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_PERMANENT, source.getControllerId(), source, game)
                .stream()
                .filter(p -> p.sharesName(permanent, game))
                .collect(Collectors.toSet());
        permanents.add(permanent);
        return player.moveCardsToExile(
                permanents, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
    }

    @Override
    public DetentionSphereEntersEffect copy() {
        return new DetentionSphereEntersEffect(this);
    }
}
