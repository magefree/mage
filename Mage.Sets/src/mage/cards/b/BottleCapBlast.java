package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class BottleCapBlast extends CardImpl {

    public BottleCapBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");


        // Improvise
        this.addAbility(new ImproviseAbility());

        // Bottle-Cap Blast deals 5 damage to any target. If excess damage was dealt to a permanent this way, create that many tapped Treasure tokens.
        this.getSpellAbility().addEffect(new BottleCapBlastEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private BottleCapBlast(final BottleCapBlast card) {
        super(card);
    }

    @Override
    public BottleCapBlast copy() {
        return new BottleCapBlast(this);
    }
}

//Based on Hell to Pay
class BottleCapBlastEffect extends OneShotEffect {

    BottleCapBlastEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 5 damage to any target. " +
                "If excess damage was dealt to a permanent this way, create that many tapped Treasure tokens.";
    }

    private BottleCapBlastEffect(final BottleCapBlastEffect effect) {
        super(effect);
    }

    @Override
    public BottleCapBlastEffect copy() {
        return new BottleCapBlastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID target = getTargetPointer().getFirst(game, source);
        Player player = game.getPlayer(target);
        if (player != null) {
            player.damage(5, source, game);
            return true;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int lethal = Math.min(permanent.getLethalDamage(source.getSourceId(), game), 5);
        permanent.damage(5, source.getSourceId(), source, game);
        if (lethal < 5) {
            new TreasureToken().putOntoBattlefield(
                    5 - lethal, game, source, source.getControllerId(), true, false
            );
        }
        return true;
    }
}
