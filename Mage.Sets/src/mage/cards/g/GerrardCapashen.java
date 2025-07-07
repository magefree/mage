package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInTargetHandCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class GerrardCapashen extends CardImpl {

    public GerrardCapashen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you gain 1 life for each card in target opponent's hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(CardsInTargetHandCount.instance)
                .setText("you gain 1 life for each card in target opponent's hand"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {3}{W}: Tap target creature. Activate this ability only if {this} is attacking.
        ability = new ActivateIfConditionActivatedAbility(
                new TapTargetEffect(), new ManaCostsImpl<>("{3}{W}"), SourceAttackingCondition.instance
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GerrardCapashen(final GerrardCapashen card) {
        super(card);
    }

    @Override
    public GerrardCapashen copy() {
        return new GerrardCapashen(this);
    }
}
