
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class DeathbellowRaider extends CardImpl {

    public DeathbellowRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathbellow Raider attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // {2}{B}: Regenerate Deathbellow Raider.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{2}{B}")));
    }

    private DeathbellowRaider(final DeathbellowRaider card) {
        super(card);
    }

    @Override
    public DeathbellowRaider copy() {
        return new DeathbellowRaider(this);
    }
}
