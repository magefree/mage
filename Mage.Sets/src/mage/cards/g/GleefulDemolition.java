package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PhyrexianGoblinToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author AhmadYProjects
 */
public final class GleefulDemolition extends CardImpl {

    public GleefulDemolition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");
        

        // Destroy target artifact. If you controlled that artifact, creature three 1/1 red Phyrexian Goblin creature tokens.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT));
        this.getSpellAbility().addEffect(new GleefulDemolitionEffect());


    }

    private GleefulDemolition(final GleefulDemolition card) {
        super(card);
    }

    @Override
    public GleefulDemolition copy() {
        return new GleefulDemolition(this);
    }
}

class GleefulDemolitionEffect extends OneShotEffect {
    GleefulDemolitionEffect(){
        super(Outcome.Benefit);
        staticText = "destroy target artifact. " +
                "if you controlled that artifact, create three 1/1 red Phyrexian Goblin creature tokens";
    }

    private GleefulDemolitionEffect(final GleefulDemolitionEffect effect) {
        super(effect);
    }

    @Override
    public GleefulDemolitionEffect copy(){
        return new GleefulDemolitionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source){
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game,source));

        if (
                permanent == null || player == null) {
            return false;
        }
        boolean isMine = permanent.isControlledBy(source.getControllerId());
        permanent.destroy(source, game, false);
        if (isMine) {
            Token token = new PhyrexianGoblinToken();
            token.putOntoBattlefield(3,game,source);

        }
        return true;
    }
}