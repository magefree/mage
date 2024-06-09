package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TorchTheWitness extends CardImpl {

    public TorchTheWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Torch the Witness deals twice X damage to target creature. If excess damage was dealt to that creature this way, investigate.
        this.getSpellAbility().addEffect(new TorchTheWitnessEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TorchTheWitness(final TorchTheWitness card) {
        super(card);
    }

    @Override
    public TorchTheWitness copy() {
        return new TorchTheWitness(this);
    }
}

class TorchTheWitnessEffect extends OneShotEffect {

    TorchTheWitnessEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals twice X damage to target creature. " +
                "If excess damage was dealt to that creature this way, investigate";
    }

    private TorchTheWitnessEffect(final TorchTheWitnessEffect effect) {
        super(effect);
    }

    @Override
    public TorchTheWitnessEffect copy() {
        return new TorchTheWitnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int lethal = permanent.getLethalDamage(source.getSourceId(), game);
        if (lethal < permanent.damage(2 * source.getManaCostsToPay().getX(), source, game)) {
            InvestigateEffect.doInvestigate(source.getControllerId(), 1, game, source);
        }
        return true;
    }
}
