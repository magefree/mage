package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HellToPay extends CardImpl {

    public HellToPay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Hell to Pay deals X damage to target creature. Create a number of tapped Treasure tokens equal to the amount of excess damage dealt to that creature this way.
        this.getSpellAbility().addEffect(new HellToPayEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HellToPay(final HellToPay card) {
        super(card);
    }

    @Override
    public HellToPay copy() {
        return new HellToPay(this);
    }
}

class HellToPayEffect extends OneShotEffect {

    HellToPayEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals X damage to target creature. Create a number of tapped " +
                "Treasure tokens equal to the amount of excess damage dealt to that creature this way";
    }

    private HellToPayEffect(final HellToPayEffect effect) {
        super(effect);
    }

    @Override
    public HellToPayEffect copy() {
        return new HellToPayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int damage = source.getManaCostsToPay().getX();
        int lethal = Math.min(permanent.getLethalDamage(source.getSourceId(), game), damage);
        permanent.damage(lethal, source.getSourceId(), source, game);
        if (damage > lethal) {
            new TreasureToken().putOntoBattlefield(
                    damage - lethal, game, source, source.getControllerId(), true, false
            );
        }
        return true;
    }
}
