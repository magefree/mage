
package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Svyatoslav28
 */
public final class TreetopRecluse extends CardImpl {

    public TreetopRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private TreetopRecluse(final TreetopRecluse card) {
        super(card);
    }

    @Override
    public TreetopRecluse copy() {
        return new TreetopRecluse(this);
    }
}
