
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class WrensRunVanquisher extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Elf card from your hand");
    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public WrensRunVanquisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As an additional cost to cast Wren's Run Vanquisher, reveal an Elf card from your hand or pay {3}.
        this.getSpellAbility().addCost(new OrCost(
                "reveal an Elf card from your hand or pay {3}", new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                new GenericManaCost(3)
        ));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private WrensRunVanquisher(final WrensRunVanquisher card) {
        super(card);
    }

    @Override
    public WrensRunVanquisher copy() {
        return new WrensRunVanquisher(this);
    }
}
