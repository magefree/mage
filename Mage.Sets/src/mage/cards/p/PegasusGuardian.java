package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.PegasusToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PegasusGuardian extends AdventureCard {

    public PegasusGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PEGASUS}, "{5}{W}",
                "Rescue the Foal",
                new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Pegasus Guardian
        this.getLeftHalfCard().setPT(3, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, if a permanent you controlled left the battlefield this turn, create a 1/1 white Pegasus creature token with flying.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new CreateTokenEffect(new PegasusToken()),
                false, RevoltCondition.instance
        ).addHint(RevoltCondition.getHint());
        ability.addWatcher(new RevoltWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // Rescue the Foal
        // Exile target creature you control, then return that card to the battlefield under its owner's control.
        this.getRightHalfCard().getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, true));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        finalizeCard();
    }

    private PegasusGuardian(final PegasusGuardian card) {
        super(card);
    }

    @Override
    public PegasusGuardian copy() {
        return new PegasusGuardian(this);
    }
}
