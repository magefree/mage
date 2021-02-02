
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class SkysnareSpider extends CardImpl {

    public SkysnareSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private SkysnareSpider(final SkysnareSpider card) {
        super(card);
    }

    @Override
    public SkysnareSpider copy() {
        return new SkysnareSpider(this);
    }
}
