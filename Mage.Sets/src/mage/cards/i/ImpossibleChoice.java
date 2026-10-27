package mage.cards.i;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenControllerTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.OfficerToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ImpossibleChoice extends CardImpl {

    public ImpossibleChoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Choose one --
        // * Surgical Separation -- Destroy target creature. Its controller creates two 1/1 red Officer creature tokens.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetEffect(new OfficerToken(), 2, false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Surgical Separation");

        // * Remain Fused -- You draw two cards and each player loses 2 life.
        Mode mode = new Mode(new DrawCardSourceControllerEffect(2, true));
        mode.addEffect(new LoseLifeAllPlayersEffect(2).concatBy("and"));
        mode.withFlavorWord("Remain Fused");
        this.getSpellAbility().addMode(mode);
    }

    private ImpossibleChoice(final ImpossibleChoice card) {
        super(card);
    }

    @Override
    public ImpossibleChoice copy() {
        return new ImpossibleChoice(this);
    }
}
