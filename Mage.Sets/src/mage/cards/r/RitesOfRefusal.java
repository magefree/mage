package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class RitesOfRefusal extends CardImpl {

    public RitesOfRefusal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Discard any number of cards. Counter target spell unless its 
        // controller pays {3} for each card discarded this way.
        this.getSpellAbility().addEffect(new RitesOfRefusalEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private RitesOfRefusal(final RitesOfRefusal card) {
        super(card);
    }

    @Override
    public RitesOfRefusal copy() {
        return new RitesOfRefusal(this);
    }
}

class RitesOfRefusalEffect extends OneShotEffect {

    RitesOfRefusalEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Discard any number of cards. Counter target "
                + "spell unless its controller pays {3} for each card discarded this way";
    }

    private RitesOfRefusalEffect(final RitesOfRefusalEffect effect) {
        super(effect);
    }

    @Override
    public RitesOfRefusalEffect copy() {
        return new RitesOfRefusalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int count = controller.discard(0, Integer.MAX_VALUE, false, source, game).size();
        return new CounterUnlessPaysEffect(new GenericManaCost(3 * count)).apply(game, source);
    }
}
