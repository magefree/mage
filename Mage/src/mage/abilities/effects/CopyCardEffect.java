package mage.abilities.effects;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.util.CardUtil;

/**
 * Created by glerman on 20/6/15.
 */
public class CopyCardEffect extends OneShotEffect {

    private final Card card;
    private final int copies;

    public CopyCardEffect(Card card, int copies) {
        super(Outcome.PutCreatureInPlay);
        this.card = card;
        this.copies = copies;
        staticText = "Put a token onto the battlefield that's a copy of {this}";
    }

    public CopyCardEffect(final CopyCardEffect effect) {
        super(effect);
        this.card = effect.card;
        this.copies = effect.copies;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            EmptyToken newToken = new EmptyToken();
            CardUtil.copyTo(newToken).from(permanent);
            return newToken.putOntoBattlefield(copies, game, source.getSourceId(), source.getControllerId());
        }
        return false;
    }

    @Override
    public CopyCardEffect copy() {
        return new CopyCardEffect(this);
    }
}
