package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class MeandersGuide extends CardImpl {

    private static final FilterControlledPermanent filter
        = new FilterControlledPermanent(SubType.MERFOLK, "another untapped Merfolk you control");
    private static final FilterCreatureCard filter2 = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.UNTAPPED);

        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public MeandersGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever this creature attacks, you may tap another untapped Merfolk you control.
        // When you do, return target creature card with mana value 3 or less from your graveyard to the battlefield.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter2));

        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
            ability,
            new TapTargetCost(new TargetControlledPermanent(filter)),
            "Tap an untapped Merfolk you control?"
        )));
    }

    private MeandersGuide(final MeandersGuide card) {
        super(card);
    }

    @Override
    public MeandersGuide copy() {
        return new MeandersGuide(this);
    }
}
