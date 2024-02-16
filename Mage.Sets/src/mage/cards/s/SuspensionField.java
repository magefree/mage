package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author awjackson
 */
public final class SuspensionField extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with toughness 3 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 2));
    }

    public SuspensionField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // When Suspension Field enters the battlefield, you may exile target creature with toughness 3 or greater until Suspension Field leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SuspensionField(final SuspensionField card) {
        super(card);
    }

    @Override
    public SuspensionField copy() {
        return new SuspensionField(this);
    }
}
