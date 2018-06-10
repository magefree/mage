
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */
public final class GutwrencherOni extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("Ogre");

    static {
        filter.add(new SubtypePredicate(SubType.OGRE));
    }

    public GutwrencherOni(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, discard a card if you don't control an Ogre.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ConditionalOneShotEffect(
                new DiscardControllerEffect(1),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0),
                "discard a card if you don't control an Ogre"), TargetController.YOU, false));

    }

    public GutwrencherOni(final GutwrencherOni card) {
        super(card);
    }

    @Override
    public GutwrencherOni copy() {
        return new GutwrencherOni(this);
    }
}

