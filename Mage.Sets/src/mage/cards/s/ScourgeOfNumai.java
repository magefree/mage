package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class ScourgeOfNumai extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Ogre");

    static {
        filter.add(SubType.OGRE.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public ScourgeOfNumai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.DEMON, SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you lose 2 life if you don't control an Ogre.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ConditionalOneShotEffect(
                        new LoseLifeSourceControllerEffect(2),
                        condition,
                        "you lose 2 life if you don't control an Ogre"
                ),
                TargetController.YOU, false
        ));
    }

    private ScourgeOfNumai(final ScourgeOfNumai card) {
        super(card);
    }

    @Override
    public ScourgeOfNumai copy() {
        return new ScourgeOfNumai(this);
    }
}
