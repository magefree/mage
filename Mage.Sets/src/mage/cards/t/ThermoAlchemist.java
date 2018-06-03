
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorcerySpell;

/**
 *
 * @author LevelX2
 */
public final class ThermoAlchemist extends CardImpl {

    public ThermoAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // {T}: Thermo-Alchemist deals 1 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT), new TapSourceCost());
        this.addAbility(ability);
        // Whenever you cast an instant or sorcery spell, untap Thermo-Alchemist.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), new FilterInstantOrSorcerySpell(), false));
    }

    public ThermoAlchemist(final ThermoAlchemist card) {
        super(card);
    }

    @Override
    public ThermoAlchemist copy() {
        return new ThermoAlchemist(this);
    }
}
