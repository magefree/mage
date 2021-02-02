
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetActivatedAbility;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class AzoriusGuildmage extends CardImpl {

    public AzoriusGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W/U}{W/U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{W}: Tap target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // {2}{U}: Counter target activated ability.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetActivatedAbility());
        this.addAbility(ability);
    }

    private AzoriusGuildmage(final AzoriusGuildmage card) {
        super(card);
    }

    @Override
    public AzoriusGuildmage copy() {
        return new AzoriusGuildmage(this);
    }
}
