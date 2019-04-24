
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CreatureEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class VigilanteJustice extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Human");

    static {
            filter.add(new SubtypePredicate(SubType.HUMAN));
    }

    public VigilanteJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");


        // Whenever a Human enters the battlefield under your control, Vigilante Justice deals 1 damage to any target.
        Ability ability = new CreatureEntersBattlefieldTriggeredAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), filter, false, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public VigilanteJustice(final VigilanteJustice card) {
        super(card);
    }

    @Override
    public VigilanteJustice copy() {
        return new VigilanteJustice(this);
    }
}
