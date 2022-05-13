
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class JeskaiCharm extends CardImpl {

    public JeskaiCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}{W}");

        // Choose one -
        // - Put target creature on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // - Jeskai Charm deals 4 damage to target opponent.
        Mode mode = new Mode(new DamageTargetEffect(4));
        mode.addTarget(new TargetOpponentOrPlaneswalker());
        this.getSpellAbility().addMode(mode);
        // - Creatures you control get +1/+1 and gain lifelink until end of turn.
        Effect effect = new BoostControlledEffect(1, 1, Duration.EndOfTurn);
        effect.setText("Creatures you control get +1/+1");
        mode = new Mode(effect);
        effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent());
        effect.setText("and gain lifelink until end of turn");
        mode.addEffect(effect);
        this.getSpellAbility().addMode(mode);
    }

    private JeskaiCharm(final JeskaiCharm card) {
        super(card);
    }

    @Override
    public JeskaiCharm copy() {
        return new JeskaiCharm(this);
    }
}
