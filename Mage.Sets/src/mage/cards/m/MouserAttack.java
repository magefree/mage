package mage.cards.m;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Robot11Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class MouserAttack extends CardImpl {

    public MouserAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one --
        // * Create a 1/1 colorless Robot artifact creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Robot11Token()));

        // * Target creature gets +3/+0 and gains first strike until end of turn.
        this.getSpellAbility().addMode(
            new Mode(new BoostTargetEffect(3, 0).setText("Target creature gets +3/+0"))
                .addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                    .setText("and gains first strike until end of turn"))
                .addTarget(new TargetCreaturePermanent())
        );
    }

    private MouserAttack(final MouserAttack card) {
        super(card);
    }

    @Override
    public MouserAttack copy() {
        return new MouserAttack(this);
    }
}
