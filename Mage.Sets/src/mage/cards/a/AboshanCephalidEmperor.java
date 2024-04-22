package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class AboshanCephalidEmperor extends CardImpl {

    static final FilterControlledPermanent filter1 = new FilterControlledPermanent("untapped Cephalid you control");
    static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures without flying");

    static {
        filter1.add(SubType.CEPHALID.getPredicate());
        filter2.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public AboshanCephalidEmperor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CEPHALID, SubType.NOBLE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tap an untapped Cephalid you control: Tap target permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapTargetCost(new TargetControlledPermanent(1, filter1)));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // {U}{U}{U}: Tap all creatures without flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapAllEffect(filter2), new ManaCostsImpl<>("{U}{U}{U}")));
    }

    private AboshanCephalidEmperor(final AboshanCephalidEmperor card) {
        super(card);
    }

    @Override
    public AboshanCephalidEmperor copy() {
        return new AboshanCephalidEmperor(this);
    }
}
