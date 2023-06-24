package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX
 */
public final class PainwrackerOni extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Ogre");

    static {
        filter.add(SubType.OGRE.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public PainwrackerOni (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.DEMON, SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Fear (This creature can't be blocked except by artifact creatures and/or black creatures.)
        this.addAbility(FearAbility.getInstance());

        // At the beginning of your upkeep, sacrifice a creature if you don't control an Ogre.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ConditionalOneShotEffect(
                        new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, null),
                        condition,
                        "sacrifice a creature if you don't control an Ogre"
                ),
                TargetController.YOU, false
        ));
    }

    public PainwrackerOni (final PainwrackerOni card) {
        super(card);
    }

    @Override
    public PainwrackerOni copy() {
        return new PainwrackerOni(this);
    }
}
