package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.CopyTokenFunction;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SpittingImage extends CardImpl {

    public SpittingImage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G/U}{G/U}");

        // Create a token that's a copy of target creature.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Retrace (You may cast this card from your graveyard by discarding a land card in addition to paying its other costs.)
        this.addAbility(new RetraceAbility(this));

    }

    private SpittingImage(final SpittingImage card) {
        super(card);
    }

    @Override
    public SpittingImage copy() {
        return new SpittingImage(this);
    }
}

class SpittingImageEffect extends OneShotEffect {

    public SpittingImageEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create a token that's a copy of target creature";
    }

    public SpittingImageEffect(final SpittingImageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            Token token = CopyTokenFunction.createTokenCopy(permanent, game);
            token.putOntoBattlefield(1, game, source, source.getControllerId());
            return true;
        }
        return false;
    }

    @Override
    public SpittingImageEffect copy() {
        return new SpittingImageEffect(this);
    }
}
