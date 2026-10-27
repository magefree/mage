package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.OfficerToken;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author muz
 */
public final class MakeItSo extends CardImpl {

    public MakeItSo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Choose one --
        // Untap target permanent you control. Target player draws two cards.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2).setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Create a 1/1 red Officer creature token and draw two cards.
        Mode mode = new Mode(new CreateTokenEffect(new OfficerToken()));
        mode.addEffect(new DrawCardSourceControllerEffect(2).concatBy("and"));
        this.getSpellAbility().addMode(mode);
    }

    private MakeItSo(final MakeItSo card) {
        super(card);
    }

    @Override
    public MakeItSo copy() {
        return new MakeItSo(this);
    }
}
