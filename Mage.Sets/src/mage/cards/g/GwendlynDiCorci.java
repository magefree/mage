package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GwendlynDiCorci extends CardImpl {

    public GwendlynDiCorci(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {T}: Target player discards a card at random. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1, true), new TapSourceCost(), MyTurnCondition.instance);
        ability.addTarget(new TargetPlayer());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private GwendlynDiCorci(final GwendlynDiCorci card) {
        super(card);
    }

    @Override
    public GwendlynDiCorci copy() {
        return new GwendlynDiCorci(this);
    }
}
