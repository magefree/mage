package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author sobiech
 */
public final class LostMonarchOfIfnir extends CardImpl {

    public LostMonarchOfIfnir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Afflict 3
        this.addAbility(new AfflictAbility(3));

        // Other Zombies you control have afflict 3.
        // At the beginning of your second main phase, if a player was dealt combat damage by a Zombie this turn, mill three cards, then you may return a creature card from your graveyard to your hand.
    }

    private LostMonarchOfIfnir(final LostMonarchOfIfnir card) {
        super(card);
    }

    @Override
    public LostMonarchOfIfnir copy() {
        return new LostMonarchOfIfnir(this);
    }
}
