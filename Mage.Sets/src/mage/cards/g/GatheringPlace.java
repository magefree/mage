package mage.cards.g;

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
public final class GatheringPlace extends CardImpl {

    public GatheringPlace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");


        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {G} or {W}. Activate only if this land entered this turn or if you control a basic land.
                this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.GreenMana(1)),
                new TapSourceCost(), SourceEnteredOrControlsBasicLandCondition.instance
        ).addHint(SourceEnteredOrControlsBasicLandCondition.getHint()));
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.WhiteMana(1)),
                new TapSourceCost(), SourceEnteredOrControlsBasicLandCondition.instance
        ).addHint(SourceEnteredOrControlsBasicLandCondition.getHint()));
    }

    private GatheringPlace(final GatheringPlace card) {
        super(card);
    }

    @Override
    public GatheringPlace copy() {
        return new GatheringPlace(this);
    }
}
