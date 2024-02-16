
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChangeATargetOfTargetSpellAbilityToSourceEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetStackObject;

/**
 *
 * @author Styxo
 */
public final class Droideka extends CardImpl {

    public Droideka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.DROID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // {2}{U}: Change a target of target spell or ability to Droideka.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChangeATargetOfTargetSpellAbilityToSourceEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetStackObject());
        this.addAbility(ability);

        // Repair 3
        this.addAbility(new RepairAbility(3));
    }

    private Droideka(final Droideka card) {
        super(card);
    }

    @Override
    public Droideka copy() {
        return new Droideka(this);
    }
}
