
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward
 */
public final class FeedThePack extends CardImpl {

    public FeedThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{5}{G}");


        // At the beginning of your end step, you may sacrifice a nontoken creature. If you do, create X 2/2 green Wolf creature tokens, where X is the sacrificed creature's toughness.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new FeedThePackEffect(), true));
    }

    private FeedThePack(final FeedThePack card) {
        super(card);
    }

    @Override
    public FeedThePack copy() {
        return new FeedThePack(this);
    }
}

class FeedThePackEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public FeedThePackEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "sacrifice a nontoken creature. If you do, create X 2/2 green Wolf creature tokens, where X is the sacrificed creature's toughness";
    }

    public FeedThePackEffect(final FeedThePackEffect effect) {
        super(effect);
    }

    @Override
    public FeedThePackEffect copy() {
        return new FeedThePackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Target target = new TargetPermanent(filter);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.choose(Outcome.PutCreatureInPlay, target, source, game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null && permanent.sacrifice(source, game)) {
                int toughness = permanent.getToughness().getValue();
                WolfToken token = new WolfToken();
                token.putOntoBattlefield(toughness, game, source, source.getControllerId());
                return true;
            }
        }
        return false;
    }
}