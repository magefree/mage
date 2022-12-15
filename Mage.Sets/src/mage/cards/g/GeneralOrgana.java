package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;

import java.util.UUID;

public class GeneralOrgana extends CardImpl {
    public GeneralOrgana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.REBEL);
        this.addSubType(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //Spells your opponents cast on your turn cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new ConditionalCostModificationEffect(
                new SpellsCostIncreasingAllEffect(2, new FilterCard(),
                        TargetController.OPPONENT),
                MyTurnCondition.instance, "Spells your opponents cast on your turn cost {2} more to cast.")));

        //When General Organa leaves the battlefield, scry 2.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ScryEffect(2), false));
    }

    public GeneralOrgana(GeneralOrgana card) {
        super(card);
    }

    @Override
    public GeneralOrgana copy() {
        return new GeneralOrgana(this);
    }
}
