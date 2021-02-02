
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SetPlayerLifeSourceEffect;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author emerald000
 */
public final class FormOfTheDragon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public FormOfTheDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}{R}{R}");

        // At the beginning of your upkeep, Form of the Dragon deals 5 damage to any target.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(5), TargetController.YOU, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // At the beginning of each end step, your life total becomes 5.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new SetPlayerLifeSourceEffect(5), TargetController.ANY, false));

        // Creatures without flying can't attack you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackYouAllEffect(Duration.WhileOnBattlefield, filter)));
    }

    private FormOfTheDragon(final FormOfTheDragon card) {
        super(card);
    }

    @Override
    public FormOfTheDragon copy() {
        return new FormOfTheDragon(this);
    }
}
