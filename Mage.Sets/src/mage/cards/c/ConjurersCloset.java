package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class ConjurersCloset extends CardImpl {

    public ConjurersCloset(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // At the beginning of your end step, you may exile target creature you control, then return that card to the battlefield under your control.
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(new ExileTargetForSourceEffect(), true);
        ability.addEffect(new ReturnToBattlefieldUnderYourControlTargetEffect().concatBy(", then"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ConjurersCloset(final ConjurersCloset card) {
        super(card);
    }

    @Override
    public ConjurersCloset copy() {
        return new ConjurersCloset(this);
    }
}
