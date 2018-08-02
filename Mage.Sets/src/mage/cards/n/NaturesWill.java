
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ControlledCreaturesDealCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class NaturesWill extends CardImpl {

    public NaturesWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // Whenever one or more creatures you control deal combat damage to a player, tap all lands that player controls and untap all lands you control.
        Effect tapAllEffect = new TapAllTargetPlayerControlsEffect(new FilterLandPermanent());
        tapAllEffect.setText("tap all lands that player controls");
        Ability ability = new ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(Zone.BATTLEFIELD, tapAllEffect, true);
        ability.addEffect(new UntapAllEffect(new FilterControlledLandPermanent()));
        addAbility(ability);
    }

    public NaturesWill(final NaturesWill card) {
        super(card);
    }

    @Override
    public NaturesWill copy() {
        return new NaturesWill(this);
    }
}
