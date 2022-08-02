package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class SilvergillDouser extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Merfolk and/or Faeries you control");

    static {
        filter.add(Predicates.or(SubType.MERFOLK.getPredicate(), SubType.FAERIE.getPredicate()));
    }

    private static final DynamicValue xValue = new SignInversionDynamicValue(new PermanentsOnBattlefieldCount(filter, null));

    public SilvergillDouser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK, SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target creature gets -X/-0 until end of turn, where X is the number of Merfolk and/or Faeries you control.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(xValue, StaticValue.get(0), Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SilvergillDouser(final SilvergillDouser card) {
        super(card);
    }

    @Override
    public SilvergillDouser copy() {
        return new SilvergillDouser(this);
    }
}
