
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class BogardanFirefiend extends CardImpl {

    public BogardanFirefiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL, SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(2), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BogardanFirefiend(final BogardanFirefiend card) {
        super(card);
    }

    @Override
    public BogardanFirefiend copy() {
        return new BogardanFirefiend(this);
    }
}
