
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SteamVents extends CardImpl {

    public SteamVents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.MOUNTAIN);

        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new PayLifeCost(2)), "you may pay 2 life. If you don't, it enters the battlefield tapped"));
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());
    }

    public SteamVents(final SteamVents card) {
        super(card);
    }

    @Override
    public SteamVents copy() {
        return new SteamVents(this);
    }

}
