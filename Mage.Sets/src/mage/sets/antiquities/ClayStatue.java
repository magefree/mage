package mage.sets.antiquities;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author Jgod
 */
public class ClayStatue extends CardImpl {

    public ClayStatue(UUID ownerId) {
        super(ownerId, 9, "Clay Statue", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.expansionSetCode = "ATQ";
        this.subtype.add("Golem");
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {2}: Regenerate Clay Statue.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{2}")));
    }

    public ClayStatue(final ClayStatue card) {
        super(card);
    }

    @Override
    public ClayStatue copy() {
        return new ClayStatue(this);
    }
}