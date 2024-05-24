package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VialSmasherGleefulGrenadier extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another outlaw");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(OutlawPredicate.instance);
    }

    public VialSmasherGleefulGrenadier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever another outlaw enters the battlefield under your control, Vial Smasher, Gleeful Grenadier deals 1 damage to target opponent.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new DamageTargetEffect(1), filter);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private VialSmasherGleefulGrenadier(final VialSmasherGleefulGrenadier card) {
        super(card);
    }

    @Override
    public VialSmasherGleefulGrenadier copy() {
        return new VialSmasherGleefulGrenadier(this);
    }
}
