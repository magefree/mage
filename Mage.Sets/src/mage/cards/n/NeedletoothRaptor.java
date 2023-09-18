
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class NeedletoothRaptor extends CardImpl {

    public NeedletoothRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Enrage</i> &mdash; Whenever Needletooth Raptor is dealt damage, it deals 5 damage to target creature an opponent controls.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new DamageTargetEffect(5).setText("it deals 5 damage to target creature an opponent controls"), false, true);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private NeedletoothRaptor(final NeedletoothRaptor card) {
        super(card);
    }

    @Override
    public NeedletoothRaptor copy() {
        return new NeedletoothRaptor(this);
    }
}
