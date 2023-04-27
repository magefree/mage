
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.abilities.keyword.ScavengeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class GolgariDecoy extends CardImpl {

    public GolgariDecoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All creatures able to block Golgari Decoy do so.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAllSourceEffect()));
        // Scavenge {3}{G}{G} ({3}{G}{G}, Exile this card from your graveyard: Put a number of +1/+1 counters equal to this card's power on target creature. Scavenge only as a sorcery.)
        this.addAbility(new ScavengeAbility(new ManaCostsImpl<>("{3}{G}{G}")));
    }

    private GolgariDecoy(final GolgariDecoy card) {
        super(card);
    }

    @Override
    public GolgariDecoy copy() {
        return new GolgariDecoy(this);
    }
}
