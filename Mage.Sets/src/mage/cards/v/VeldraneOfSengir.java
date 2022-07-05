
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class VeldraneOfSengir extends CardImpl {

    public VeldraneOfSengir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {1}{B}{B}: Veldrane of Sengir gets -3/-0 and gains forestwalk until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(-3, -0, Duration.EndOfTurn).setText("{this} gets -3/-0"), new ManaCostsImpl<>("{1}{B}{B}"));
        ability.addEffect(new GainAbilitySourceEffect(new ForestwalkAbility(false), Duration.EndOfTurn).setText("and gains forestwalk until end of turn"));
        this.addAbility(ability);
    }

    private VeldraneOfSengir(final VeldraneOfSengir card) {
        super(card);
    }

    @Override
    public VeldraneOfSengir copy() {
        return new VeldraneOfSengir(this);
    }
}
