package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.AttachTargetToTargetEffect;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class SwordsmanSharpScoundrel extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.VILLAIN, "another Villain");
    private static final FilterCreaturePermanent filter2
        = new FilterCreaturePermanent("equipped creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(EquippedPredicate.instance);
    }

    public SwordsmanSharpScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another Villain you control enters, attach up to one target Equipment you control to target creature you control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
            Zone.BATTLEFIELD, new AttachTargetToTargetEffect(), filter, false
        );
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever an equipped creature you control attacks, it connives.
        this.addAbility(new AttacksAllTriggeredAbility(
            new ConniveTargetEffect(), false, filter2, SetTargetPointer.PERMANENT, false
        ));
    }

    private SwordsmanSharpScoundrel(final SwordsmanSharpScoundrel card) {
        super(card);
    }

    @Override
    public SwordsmanSharpScoundrel copy() {
        return new SwordsmanSharpScoundrel(this);
    }
}
