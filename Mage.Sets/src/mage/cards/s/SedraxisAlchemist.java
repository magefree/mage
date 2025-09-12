package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SedraxisAlchemist extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control a blue permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public SedraxisAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sedraxis Alchemist enters the battlefield, if you control a blue permanent, return target nonland permanent to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false).withInterveningIf(condition);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private SedraxisAlchemist(final SedraxisAlchemist card) {
        super(card);
    }

    @Override
    public SedraxisAlchemist copy() {
        return new SedraxisAlchemist(this);
    }
}
