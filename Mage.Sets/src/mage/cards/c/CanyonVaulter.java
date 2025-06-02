package mage.cards.c;

import mage.MageInt;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CrewsVehicleSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class CanyonVaulter extends CardImpl {

    public CanyonVaulter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever this creature saddles a Mount or crews a Vehicle during your main phase, that Mount or Vehicle gains flying until end of turn.
        Effect effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("that Mount or Vehicle gains flying until end of turn");
        this.addAbility(new CrewsVehicleSourceTriggeredAbility(effect, true, true));
    }

    private CanyonVaulter(final CanyonVaulter card) {
        super(card);
    }

    @Override
    public CanyonVaulter copy() {
        return new CanyonVaulter(this);
    }
}
