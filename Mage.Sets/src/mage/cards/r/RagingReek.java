
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class RagingReek extends CardImpl {

    public RagingReek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Raging Reek attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        //{5}{R}: Monstrosity 2
        this.addAbility(new MonstrosityAbility("{5}{R}", 2));
    }

    private RagingReek(final RagingReek card) {
        super(card);
    }

    @Override
    public RagingReek copy() {
        return new RagingReek(this);
    }
}
