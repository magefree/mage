package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author AhmadYProjects
 */
public final class CinderslashRavager extends CardImpl {

    public CinderslashRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell costs {1} less to cast for each permanent you control with oil counters on it.
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Cinderslash Ravager enters the battlefield, it deals 1 damage to each creature your opponents control.
    }

    private CinderslashRavager(final CinderslashRavager card) {
        super(card);
    }

    @Override
    public CinderslashRavager copy() {
        return new CinderslashRavager(this);
    }
}
