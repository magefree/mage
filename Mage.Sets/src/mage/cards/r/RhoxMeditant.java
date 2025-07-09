package mage.cards.r;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class RhoxMeditant extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control a green permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public RhoxMeditant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Rhox Meditant enters the battlefield, if you control a green permanent, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)).withInterveningIf(condition));
    }

    private RhoxMeditant(final RhoxMeditant card) {
        super(card);
    }

    @Override
    public RhoxMeditant copy() {
        return new RhoxMeditant(this);
    }
}
