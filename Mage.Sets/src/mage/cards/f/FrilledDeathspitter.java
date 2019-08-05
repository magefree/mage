
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author TheElk801 & L_J
 */
public final class FrilledDeathspitter extends CardImpl {

    public FrilledDeathspitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Enrage</i> &mdash; Whenever Frilled Deathspitter is dealt damage, it deals 2 damage to target opponent.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new DamageTargetEffect(2).setText("it deals 2 damage to target opponent or planeswalker"), false, true
        );
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    public FrilledDeathspitter(final FrilledDeathspitter card) {
        super(card);
    }

    @Override
    public FrilledDeathspitter copy() {
        return new FrilledDeathspitter(this);
    }
}
