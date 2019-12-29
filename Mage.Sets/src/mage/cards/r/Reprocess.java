
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LoneFox

 */
public final class Reprocess extends CardImpl {

    public Reprocess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Sacrifice any number of artifacts, creatures, and/or lands. Draw a card for each permanent sacrificed this way.
        this.getSpellAbility().addEffect(new ReprocessEffect());
    }

    public Reprocess(final Reprocess card) {
        super(card);
    }

    @Override
    public Reprocess copy() {
        return new Reprocess(this);
    }
}

class ReprocessEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts, creatures, and/or lands");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE),
            new CardTypePredicate(CardType.LAND)));
    }

    public ReprocessEffect() {
        super(Outcome.Neutral);
        staticText  = "Sacrifice any number of artifacts, creatures, and/or lands. Draw a card for each permanent sacrificed this way.";
    }

    public ReprocessEffect(final ReprocessEffect effect) {
        super(effect);
    }

    @Override
    public ReprocessEffect copy() {
        return new ReprocessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null){
            return false;
        }
        int amount = 0;
        TargetControlledPermanent toSacrifice = new TargetControlledPermanent(0, Integer.MAX_VALUE, filter, true);
        if(player.chooseTarget(Outcome.Sacrifice, toSacrifice, source, game)) {
            for(UUID uuid : toSacrifice.getTargets()){
                Permanent permanent = game.getPermanent(uuid);
                if(permanent != null){
                    permanent.sacrifice(source.getSourceId(), game);
                    amount++;
                }
            }
            player.drawCards(amount, game);
        }
        return true;
    }
}
