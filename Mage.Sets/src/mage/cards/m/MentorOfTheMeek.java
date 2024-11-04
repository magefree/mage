package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author Loki
 */
public final class MentorOfTheMeek extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another creature you control with power 2 or less");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public MentorOfTheMeek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //Whenever another creature with power 2 or less you control enters, you may pay 1. If you do, draw a card.
        Effect effect = new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}"));
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, effect, filter, false);
        this.addAbility(ability);

    }

    private MentorOfTheMeek(final MentorOfTheMeek card) {
        super(card);
    }

    @Override
    public MentorOfTheMeek copy() {
        return new MentorOfTheMeek(this);
    }

}
