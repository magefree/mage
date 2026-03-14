
package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class VigilanteJustice extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.HUMAN);

    public VigilanteJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever a Human you control enters, Vigilante Justice deals 1 damage to any target.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(new DamageTargetEffect(1), filter);
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
