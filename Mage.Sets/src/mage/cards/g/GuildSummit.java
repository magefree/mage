package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class GuildSummit extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Gate");

    static {
        filter.add(new SubtypePredicate(SubType.GATE));
    }

    public GuildSummit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When Guild Summit enters the battlefield, you may tap any number of untapped Gates you control. Draw a card for each Gate tapped this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new GuildSummitEffect(), true
        ));

        // Whenever a Gate enters the battlefield under your control, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter
        ));
    }

    public GuildSummit(final GuildSummit card) {
        super(card);
    }

    @Override
    public GuildSummit copy() {
        return new GuildSummit(this);
    }
}

class GuildSummitEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterPermanent("untapped Gates you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(TappedPredicate.instance));
        filter.add(new SubtypePredicate(SubType.GATE));
    }

    public GuildSummitEffect() {
        super(Outcome.GainLife);
        staticText = "you may tap any number of untapped Gates you control. "
                + "Draw a card for each Gate tapped this way";
    }

    public GuildSummitEffect(GuildSummitEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int tappedAmount = 0;
        Player you = game.getPlayer(source.getControllerId());
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        if (target.canChoose(source.getControllerId(), game) && target.choose(Outcome.Tap, source.getControllerId(), source.getSourceId(), game)) {
            for (UUID creature : target.getTargets()) {
                if (creature != null) {
                    game.getPermanent(creature).tap(game);
                    tappedAmount++;
                }
            }
        }
        if (tappedAmount > 0) {
            you.drawCards(tappedAmount, game);
            return true;
        }
        return false;
    }

    @Override
    public GuildSummitEffect copy() {
        return new GuildSummitEffect(this);
    }

}
