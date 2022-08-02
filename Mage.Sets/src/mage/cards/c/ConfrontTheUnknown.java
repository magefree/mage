package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ConfrontTheUnknown extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.CLUE, "Clue you control"));

    public ConfrontTheUnknown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Investigate, then target creature gets +1/+1 until end of turn for each Clue you control.
        Effect effect = new InvestigateEffect();
        effect.setText("Investigate");
        getSpellAbility().addEffect(effect);
        effect = new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn);
        effect.setText(", then target creature gets +1/+1 until end of turn for each Clue you control. <i>(To investigate, "
                + "create a colorless Clue artifact token with \"{2}, Sacrifice this artifact: Draw a card.\")</i>");
        getSpellAbility().addEffect(effect);
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ConfrontTheUnknown(final ConfrontTheUnknown card) {
        super(card);
    }

    @Override
    public ConfrontTheUnknown copy() {
        return new ConfrontTheUnknown(this);
    }
}
