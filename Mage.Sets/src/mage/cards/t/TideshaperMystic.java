package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TideshaperMystic extends CardImpl {

    public TideshaperMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Target land becomes the basic land type of your choice until end of turn. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new BecomesBasicLandTargetEffect(Duration.EndOfTurn), new TapSourceCost(), MyTurnCondition.instance);
        ability.addTarget(new TargetLandPermanent());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private TideshaperMystic(final TideshaperMystic card) {
        super(card);
    }

    @Override
    public TideshaperMystic copy() {
        return new TideshaperMystic(this);
    }
}
