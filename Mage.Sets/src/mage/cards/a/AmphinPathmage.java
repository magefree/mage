
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AmphinPathmage extends CardImpl {

    public AmphinPathmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {2}{U}: Target creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private AmphinPathmage(final AmphinPathmage card) {
        super(card);
    }

    @Override
    public AmphinPathmage copy() {
        return new AmphinPathmage(this);
    }
}
