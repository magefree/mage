package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.AttackedByCreatureTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author TheElk801
 */
public final class BarbedFoliage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public BarbedFoliage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // Whenever a creature attacks you, it loses flanking until end of turn.
        this.addAbility(new AttackedByCreatureTriggeredAbility(
                new LoseAbilityTargetEffect(new FlankingAbility())
                        .setText("it loses flanking until end of turn"),
                false, SetTargetPointer.PERMANENT
        ));

        // Whenever a creature without flying attacks you, Barbed Foliage deals 1 damage to it.
        this.addAbility(new AttackedByCreatureTriggeredAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(1)
                        .setText("{this} deals 1 damage to it"),
                false, SetTargetPointer.PERMANENT, filter
        ));
    }

    private BarbedFoliage(final BarbedFoliage card) {
        super(card);
    }

    @Override
    public BarbedFoliage copy() {
        return new BarbedFoliage(this);
    }
}
