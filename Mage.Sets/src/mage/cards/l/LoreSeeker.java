
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;

/**
 *
 * @author tiera3 - based on PrizefighterConstruct and CanalDredger
 * note - draftmatters ability not implemented
 */
public final class LoreSeeker extends CardImpl {

    public LoreSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // TODO: Draft specific abilities not implemented
        // Reveal Lore Seeker as you draft it. After you draft Lore Seeker, you may add a booster pack to the draft.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Reveal Lore Seeker as you draft it. "
                + "After you draft Lore Seeker, you may add a booster pack to the draft - not implemented.")));
    }

    private LoreSeeker(final LoreSeeker card) {
        super(card);
    }

    @Override
    public LoreSeeker copy() {
        return new LoreSeeker(this);
    }
}
