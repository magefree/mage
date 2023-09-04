
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class ShelteringWord extends CardImpl {

    public ShelteringWord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Target creature you control gains hexproof until end of turn. You gain life equal to that creature's toughness.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ShelteringWordEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private ShelteringWord(final ShelteringWord card) {
        super(card);
    }

    @Override
    public ShelteringWord copy() {
        return new ShelteringWord(this);
    }
}

class ShelteringWordEffect extends OneShotEffect {

    public ShelteringWordEffect() {
        super(Outcome.GainLife);
        this.staticText = "You gain life equal to that creature's toughness";
    }

    private ShelteringWordEffect(final ShelteringWordEffect effect) {
        super(effect);
    }

    @Override
    public ShelteringWordEffect copy() {
        return new ShelteringWordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player != null && permanent != null) {
            int amount = permanent.getToughness().getValue();
            if (amount > 0) {
                player.gainLife(amount, game, source);
                return true;
            }
        }
        return false;
    }
}
