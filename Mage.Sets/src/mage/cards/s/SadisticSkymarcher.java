
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
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
public final class SadisticSkymarcher extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Vampire card from your hand");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public SadisticSkymarcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As an additional cost to cast Sadistic Skymarcher, reveal a Vampire card from your hand or pay {1}.
        this.getSpellAbility().addCost(new OrCost(
                "reveal a Vampire card from your hand or pay {1}", new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                new GenericManaCost(1)
        ));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private SadisticSkymarcher(final SadisticSkymarcher card) {
        super(card);
    }

    @Override
    public SadisticSkymarcher copy() {
        return new SadisticSkymarcher(this);
    }
}
