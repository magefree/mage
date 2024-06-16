
package mage.cards.c;

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
public final class CogworkLibrarian extends CardImpl {

    public CogworkLibrarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // TODO: Draft specific abilities not implemented
        // Draft Cogwork Librarian face up.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Draft Cogwork Librarian face up - not implemented.")));

        // As you draft a card, you may draft an additional card from that booster pack. If you do, put Cogwork Librarian into that booster pack.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("As you draft a card, "
                + "you may draft an additional card from that booster pack. If you do, "
                + "put Cogwork Librarian into that booster pack - not implemented.")));
    }

    private CogworkLibrarian(final CogworkLibrarian card) {
        super(card);
    }

    @Override
    public CogworkLibrarian copy() {
        return new CogworkLibrarian(this);
    }
}
