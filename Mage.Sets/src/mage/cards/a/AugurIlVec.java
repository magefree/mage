package mage.cards.a;

import mage.MageInt;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AugurIlVec extends CardImpl {

    public AugurIlVec(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());

        // Sacrifice Augur il-Vec: You gain 4 life. Activate this ability only during your upkeep.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new GainLifeEffect(4), new SacrificeSourceCost(), IsStepCondition.getMyUpkeep()
        ));
    }

    private AugurIlVec(final AugurIlVec card) {
        super(card);
    }

    @Override
    public AugurIlVec copy() {
        return new AugurIlVec(this);
    }
}
