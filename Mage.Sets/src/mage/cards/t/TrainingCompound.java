package mage.cards.t;

import java.util.UUID;

import mage.Mana;
import mage.abilities.condition.common.SourceEnteredOrControlsBasicLandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author muz
 */
public final class TrainingCompound extends CardImpl {

    public TrainingCompound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");


        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {R} or {G}. Activate only if this land entered this turn or if you control a basic land.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.RedMana(1)),
                new TapSourceCost(), SourceEnteredOrControlsBasicLandCondition.instance
        ).addHint(SourceEnteredOrControlsBasicLandCondition.getHint()));
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.GreenMana(1)),
                new TapSourceCost(), SourceEnteredOrControlsBasicLandCondition.instance
        ).addHint(SourceEnteredOrControlsBasicLandCondition.getHint()));
    }

    private TrainingCompound(final TrainingCompound card) {
        super(card);
    }

    @Override
    public TrainingCompound copy() {
        return new TrainingCompound(this);
    }
}
