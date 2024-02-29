package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TargetPlayerGainControlSourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class HumbleDefector extends CardImpl {

    public HumbleDefector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Draw two cards. Target opponent gains control of Humble Defector. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(2), new TapSourceCost(), MyTurnCondition.instance);
        ability.addEffect(new TargetPlayerGainControlSourceEffect());
        ability.addTarget(new TargetOpponent());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);

    }

    private HumbleDefector(final HumbleDefector card) {
        super(card);
    }

    @Override
    public HumbleDefector copy() {
        return new HumbleDefector(this);
    }
}
