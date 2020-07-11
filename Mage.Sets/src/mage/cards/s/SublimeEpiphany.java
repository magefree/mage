package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetActivatedOrTriggeredAbility;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SublimeEpiphany extends CardImpl {

    public SublimeEpiphany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Choose one or more —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(5);

        // • Counter target spell
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // • Counter target activated or triggered ability.
        Mode mode = new Mode(new CounterTargetEffect());
        mode.addTarget(new TargetActivatedOrTriggeredAbility());
        this.getSpellAbility().addMode(mode);

        // • Return target nonland permanent to its owner's hand.
        mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addMode(mode);

        // • Create a token that's a copy of target creature you control.
        mode = new Mode(new CreateTokenCopyTargetEffect());
        mode.addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // • Target player draws a card.
        mode = new Mode(new DrawCardTargetEffect(1));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private SublimeEpiphany(final SublimeEpiphany card) {
        super(card);
    }

    @Override
    public SublimeEpiphany copy() {
        return new SublimeEpiphany(this);
    }
}
