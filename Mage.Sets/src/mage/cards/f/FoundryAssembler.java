
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class FoundryAssembler extends CardImpl {

    public FoundryAssembler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Improvise <i>(Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1).)
        addAbility(new ImproviseAbility());
    }

    private FoundryAssembler(final FoundryAssembler card) {
        super(card);
    }

    @Override
    public FoundryAssembler copy() {
        return new FoundryAssembler(this);
    }
}
