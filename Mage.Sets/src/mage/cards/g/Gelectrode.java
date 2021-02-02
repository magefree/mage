
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class Gelectrode extends CardImpl {

    public Gelectrode(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{R}");
        this.subtype.add(SubType.WEIRD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), new FilterInstantOrSorcerySpell("an instant or sorcery spell"), true));
    }

    private Gelectrode(final Gelectrode card) {
        super(card);
    }

    @Override
    public Gelectrode copy() {
        return new Gelectrode(this);
    }
}
