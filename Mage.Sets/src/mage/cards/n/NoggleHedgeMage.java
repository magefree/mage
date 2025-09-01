package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NoggleHedgeMage extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.ISLAND, "you control two or more Islands"),
            ComparisonType.MORE_THAN, 1
    );
    private static final Condition condition2 = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.MOUNTAIN, "you control two or more Mountains"),
            ComparisonType.MORE_THAN, 1
    );

    public NoggleHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/R}");
        this.subtype.add(SubType.NOGGLE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Noggle Hedge-Mage enters the battlefield, if you control two or more Islands, you may tap two target permanents.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), true).withInterveningIf(condition);
        ability.addTarget(new TargetPermanent(2, StaticFilters.FILTER_PERMANENTS));
        this.addAbility(ability);

        // When Noggle Hedge-Mage enters the battlefield, if you control two or more Mountains, you may have Noggle Hedge-Mage deal 2 damage to target player.
        ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2), true).withInterveningIf(condition2);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private NoggleHedgeMage(final NoggleHedgeMage card) {
        super(card);
    }

    @Override
    public NoggleHedgeMage copy() {
        return new NoggleHedgeMage(this);
    }
}
