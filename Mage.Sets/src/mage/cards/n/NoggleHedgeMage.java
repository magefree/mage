
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class NoggleHedgeMage extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();
    private static final FilterLandPermanent filter2 = new FilterLandPermanent();

    static {
        filter.add(SubType.ISLAND.getPredicate());
        filter2.add(SubType.MOUNTAIN.getPredicate());
    }

    private static final String rule = "When {this} enters the battlefield, if you control two or more Islands, you may tap two target permanents.";
    private static final String rule2 = "When {this} enters the battlefield, if you control two or more Mountains, you may have {this} deal 2 damage to target player or planeswalker.";

    public NoggleHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/R}");
        this.subtype.add(SubType.NOGGLE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Noggle Hedge-Mage enters the battlefield, if you control two or more Islands, you may tap two target permanents.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), true), new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1), rule);
        ability.addTarget(new TargetPermanent(2, new FilterPermanent()));
        this.addAbility(ability);

        // When Noggle Hedge-Mage enters the battlefield, if you control two or more Mountains, you may have Noggle Hedge-Mage deal 2 damage to target player.
        Ability ability2 = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2), true), new PermanentsOnTheBattlefieldCondition(filter2, ComparisonType.MORE_THAN, 1), rule2);
        ability2.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability2);
    }

    private NoggleHedgeMage(final NoggleHedgeMage card) {
        super(card);
    }

    @Override
    public NoggleHedgeMage copy() {
        return new NoggleHedgeMage(this);
    }
}
