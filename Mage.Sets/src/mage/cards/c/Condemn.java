package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Condemn extends CardImpl {

    public Condemn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Put target attacking creature on the bottom of its owner's library.
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(false));

        // Its controller gains life equal to its toughness.
        this.getSpellAbility().addEffect(new CondemnEffect());
    }

    private Condemn(final Condemn card) {
        super(card);
    }

    @Override
    public Condemn copy() {
        return new Condemn(this);
    }
}

class CondemnEffect extends OneShotEffect {

    public CondemnEffect() {
        super(Outcome.Detriment);
        staticText = "Its controller gains life equal to its toughness";
    }

    public CondemnEffect(final CondemnEffect effect) {
        super(effect);
    }

    @Override
    public CondemnEffect copy() {
        return new CondemnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.gainLife(permanent.getToughness().getValue(), game, source);
                return true;
            }
        }
        return false;
    }
}
