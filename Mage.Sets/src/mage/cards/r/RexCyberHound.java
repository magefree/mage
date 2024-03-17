package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class RexCyberHound extends CardImpl {

    public RexCyberHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Rex, Cyber-Hound deals combat damage to a player, they mill two cards and you get {E}{E}.
        // Pay {E}{E}: Choose target creature card in a graveyard. Exile it with a brain counter on it. Activate only as a sorcery.
        // Rex has all activated abilities of all cards in exile with brain counters on them.
    }

    private RexCyberHound(final RexCyberHound card) {
        super(card);
    }

    @Override
    public RexCyberHound copy() {
        return new RexCyberHound(this);
    }
}
