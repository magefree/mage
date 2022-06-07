package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThassaDeepDwelling extends CardImpl {

    private static final FilterPermanent filterAnother = new FilterCreaturePermanent("another target creature");
    private static final FilterPermanent filterOther = new FilterControlledCreaturePermanent("other target creature you control");

    static {
        filterAnother.add(AnotherPredicate.instance);
        filterOther.add(AnotherPredicate.instance);
    }

    public ThassaDeepDwelling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to blue is less than five, Thassa isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.U, 5))
                .addHint(DevotionCount.U.getHint()));

        // At the beginning of your end step, exile up to one other target creature you control, then return that card to the battlefield under your control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new ExileTargetForSourceEffect(),
                TargetController.YOU, false
        );
        ability.addEffect(new ReturnToBattlefieldUnderYourControlTargetEffect());
        ability.addTarget(new TargetPermanent(
                0, 1, filterOther, false
        ));
        this.addAbility(ability);

        // {3}{U}: Tap another target creature.
        ability = new SimpleActivatedAbility(
                new TapTargetEffect("tap another target creature"), new ManaCostsImpl("{3}{U}")
        );
        ability.addTarget(new TargetPermanent(filterAnother));
        this.addAbility(ability);
    }

    private ThassaDeepDwelling(final ThassaDeepDwelling card) {
        super(card);
    }

    @Override
    public ThassaDeepDwelling copy() {
        return new ThassaDeepDwelling(this);
    }
}
