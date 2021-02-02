
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ViscidLemures extends CardImpl {

    public ViscidLemures(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {0}: Viscid Lemures gets -1/-0 and gains swampwalk until end of turn.
        Effect effect = new BoostSourceEffect(-1, 0, Duration.EndOfTurn);
        effect.setText("{this} gets -1/-0");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(0));
        effect = new GainAbilitySourceEffect(new SwampwalkAbility(), Duration.EndOfTurn);
        effect.setText("and gains swampwalk until end of turn. <i>(It can't be blocked as long as defending player controls a Swamp.)</i>");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private ViscidLemures(final ViscidLemures card) {
        super(card);
    }

    @Override
    public ViscidLemures copy() {
        return new ViscidLemures(this);
    }
}
