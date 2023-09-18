package mage.cards.p;

import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PowerOfPersuasion extends CardImpl {

    public PowerOfPersuasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Choose target creature an opponent controls, then roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "choose target creature an opponent controls, then roll a d20"
        );
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        // 1-9 | Return it to its owner's hand.
        effect.addTableEntry(1, 9, new ReturnToHandTargetEffect().setText("return it to its owner's hand"));

        // 10-19 | Its owner puts it on the top of bottom of their library.
        effect.addTableEntry(10, 19, new PutOnTopOrBottomLibraryTargetEffect(
                "its owner puts it on the top of bottom of their library"
        ));

        // 20 | Gain control of it until the end of your next turn.
        effect.addTableEntry(20, 20, new GainControlTargetEffect(
                Duration.UntilEndOfYourNextTurn, true
        ).setText("gain control of it until the end of your next turn"));
    }

    private PowerOfPersuasion(final PowerOfPersuasion card) {
        super(card);
    }

    @Override
    public PowerOfPersuasion copy() {
        return new PowerOfPersuasion(this);
    }
}
