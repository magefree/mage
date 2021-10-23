
package mage.cards.a;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public final class AllIsDust extends CardImpl {

    public AllIsDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.SORCERY},"{7}");
        this.subtype.add(SubType.ELDRAZI);
        this.getSpellAbility().addEffect(new AllIsDustEffect());
    }

    private AllIsDust(final AllIsDust card) {
        super(card);
    }

    @Override
    public AllIsDust copy() {
        return new AllIsDust(this);
    }
}

class AllIsDustEffect extends OneShotEffect {

    AllIsDustEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Each player sacrifices all permanents they control that are one or more colors";
    }

    AllIsDustEffect(final AllIsDustEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(source.getControllerId(), game);
        for (Permanent p : permanents) {
            if (!p.getColor(game).isColorless()) {
                p.sacrifice(source, game);
            }
        }

        return true;
    }

    @Override
    public AllIsDustEffect copy() {
        return new AllIsDustEffect(this);
    }
}
