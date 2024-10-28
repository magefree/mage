package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 *
 * @author Rafbill
 */
public final class FelidarSovereign extends CardImpl {

    public FelidarSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your upkeep, if you have 40 or more life, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), false)
                .withInterveningIf(new LifeCompareCondition(TargetController.YOU, ComparisonType.OR_GREATER, 40)));
    }

    private FelidarSovereign(final FelidarSovereign card) {
        super(card);
    }

    @Override
    public FelidarSovereign copy() {
        return new FelidarSovereign(this);
    }
}
