package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PhyrexianGoblinToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author AhmadYProjects
 */
public final class GleefulDemolition extends CardImpl {

    public GleefulDemolition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");
        

        // Destroy target artifact. If you controlled that artifact, creature three 1/1 red Phyrexian Goblin creature tokens.
        this.getSpellAbility().addEffect(new GleefulDemolitionEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private GleefulDemolition(final GleefulDemolition card) {
        super(card);
    }

    @Override
    public GleefulDemolition copy() {
        return new GleefulDemolition(this);
    }
}

class GleefulDemolitionEffect extends DestroyTargetEffect {
    GleefulDemolitionEffect(){
        ;
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
        if (isMine) {
            Token token = new PhyrexianGoblinToken();
            token.putOntoBattlefield(3,game,source);

        }
        return true;
    }
}