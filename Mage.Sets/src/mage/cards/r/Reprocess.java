
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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetSacrifice;

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

    private Reprocess(final Reprocess card) {
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
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate(),
            CardType.LAND.getPredicate()));
    }

    public ReprocessEffect() {
        super(Outcome.Neutral);
        staticText  = "Sacrifice any number of artifacts, creatures, and/or lands. Draw a card for each permanent sacrificed this way.";
    }

    private ReprocessEffect(final ReprocessEffect effect) {
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
        TargetSacrifice toSacrifice = new TargetSacrifice(0, Integer.MAX_VALUE, filter);
        if(player.choose(Outcome.Sacrifice, toSacrifice, source, game)) {
            for(UUID uuid : toSacrifice.getTargets()){
                Permanent permanent = game.getPermanent(uuid);
                if(permanent != null){
                    permanent.sacrifice(source, game);
                    amount++;
                }
            }
            player.drawCards(amount, source, game);
        }
        return true;
    }
}
