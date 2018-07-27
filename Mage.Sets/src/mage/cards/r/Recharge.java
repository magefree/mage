package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Recharge extends CardImpl {

    public Recharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}");
        

        // Choose one -
        //   Exile target creature you control, then return it to the battlefield under its owner's control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileTargetForSourceEffect());
        this.getSpellAbility().addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect());

        //   You gain 5 life.
        Mode mode = new Mode();
        mode.getEffects().add(new GainLifeEffect(5));
        this.getSpellAbility().addMode(mode);

        //   Draw two cards, then discard a card.
        mode = new Mode();
        mode.getEffects().add(new DrawCardSourceControllerEffect(2));
        mode.getEffects().add(new DiscardControllerEffect(1, false));
        this.getSpellAbility().addMode(mode);
    }

    public Recharge(final Recharge card) {
        super(card);
    }

    @Override
    public Recharge copy() {
        return new Recharge(this);
    }
}
