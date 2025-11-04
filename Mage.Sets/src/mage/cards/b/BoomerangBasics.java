package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoomerangBasics extends CardImpl {

    public BoomerangBasics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        this.subtype.add(SubType.LESSON);

        // Return target nonland permanent to its owner's hand. If you controlled that permanent, draw a card.
        this.getSpellAbility().addEffect(new BoomerangBasicsEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private BoomerangBasics(final BoomerangBasics card) {
        super(card);
    }

    @Override
    public BoomerangBasics copy() {
        return new BoomerangBasics(this);
    }
}

class BoomerangBasicsEffect extends OneShotEffect {

    BoomerangBasicsEffect() {
        super(Outcome.Benefit);
        staticText = "return target nonland permanent to its owner's hand. If you controlled that permanent, draw a card";
    }

    private BoomerangBasicsEffect(final BoomerangBasicsEffect effect) {
        super(effect);
    }

    @Override
    public BoomerangBasicsEffect copy() {
        return new BoomerangBasicsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        boolean flag = permanent.isControlledBy(player.getId());
        player.moveCards(permanent, Zone.HAND, source, game);
        if (flag) {
            game.processAction();
            player.drawCards(1, source, game);
        }
        return true;
    }
}
