
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class StirThePride extends CardImpl {

    public StirThePride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{W}");


        // Choose one - 
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Creatures you control get +2/+2 until end of turn;
        this.getSpellAbility().addEffect(new BoostControlledEffect(2,2, Duration.EndOfTurn));
        // or until end of turn, creatures you control gain "Whenever this creature deals damage, you gain that much life."
        Effect effect = new GainAbilityControlledEffect(new DealsDamageGainLifeSourceTriggeredAbility(), Duration.EndOfTurn);
        effect.setText("until end of turn, creatures you control gain \"Whenever this creature deals damage, you gain that much life.\"");
        Mode mode = new Mode(effect);
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {1}{W}
        this.addAbility(new EntwineAbility("{1}{W}"));

    }

    private StirThePride(final StirThePride card) {
        super(card);
    }

    @Override
    public StirThePride copy() {
        return new StirThePride(this);
    }
}
