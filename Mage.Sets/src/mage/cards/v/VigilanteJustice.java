
package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class VigilanteJustice extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a Human");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public VigilanteJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");


        // Whenever a Human enters the battlefield under your control, Vigilante Justice deals 1 damage to any target.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(1),
                filter,
                false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private VigilanteJustice(final VigilanteJustice card) {
        super(card);
    }

    @Override
    public VigilanteJustice copy() {
        return new VigilanteJustice(this);
    }
}
