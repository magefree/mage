package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TempleElder extends CardImpl {

    public TempleElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: You gain 1 life. Activate this ability only during your turn, before attackers are declared.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new GainLifeEffect(1), new TapSourceCost(),
                MyTurnBeforeAttackersDeclaredCondition.instance
        ));
    }

    private TempleElder(final TempleElder card) {
        super(card);
    }

    @Override
    public TempleElder copy() {
        return new TempleElder(this);
    }
}
