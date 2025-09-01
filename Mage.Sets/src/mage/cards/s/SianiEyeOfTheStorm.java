package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValuePositiveHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SianiEyeOfTheStorm extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("attacking creatures with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(AttackingPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValuePositiveHint("Number of Rats you control", xValue);

    public SianiEyeOfTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Siani, Eye of the Storm attacks, scry X, where X is the number of attacking creatures with flying.
        this.addAbility(new AttacksTriggeredAbility(new ScryEffect(xValue)).addHint(hint));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private SianiEyeOfTheStorm(final SianiEyeOfTheStorm card) {
        super(card);
    }

    @Override
    public SianiEyeOfTheStorm copy() {
        return new SianiEyeOfTheStorm(this);
    }
}