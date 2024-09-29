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
 * @author tiera3 - based on PrizefighterConstruct
 * note - draftmatters ability not implemented
 */
public final class LeovoldsOperative extends CardImpl {

    public LeovoldsOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // TODO: Draft specific abilities not implemented
        // Draft Leovold’s Operative face up.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Draft Leovold's Operative face up - not implemented.")));

        // As you draft a card, you may draft an additional card from that booster pack. If you do, turn Leovold's Operative face down, then pass the next booster pack without drafting a card from it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("As you draft a card, you may draft an additional card from that booster pack. "
                + "If you do, turn Leovold's Operative face down, then pass the next booster pack without drafting a card from it - not implemented.")));
    }

    private LeovoldsOperative(final LeovoldsOperative card) {
        super(card);
    }

    @Override
    public LeovoldsOperative copy() {
        return new LeovoldsOperative(this);
    }
}
