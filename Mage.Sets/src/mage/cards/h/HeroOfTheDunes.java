package mage.cards.h;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeroOfTheDunes extends CardImpl {

    private static final Predicate<MageObject> predicate
            = new ManaValuePredicate(ComparisonType.FEWER_THAN, 4);
    private static final FilterCard filter
            = new FilterCard("artifact or creature card with mana value 3 or less from your graveyard");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent("creatures you control with mana value 3 or less");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(predicate);
        filter2.add(TargetController.YOU.getControllerPredicate());
        filter2.add(predicate);
    }

    public HeroOfTheDunes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Hero of the Dunes enters the battlefield, return target artifact or creature card with mana value 3 or less from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Creatures you control with mana value 3 or less get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 0, Duration.WhileOnBattlefield, filter2, false
        )));
    }

    private HeroOfTheDunes(final HeroOfTheDunes card) {
        super(card);
    }

    @Override
    public HeroOfTheDunes copy() {
        return new HeroOfTheDunes(this);
    }
}
