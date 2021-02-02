
package mage.cards.z;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Rystan
 */
public final class ZhalfirinVoid extends CardImpl {

    public ZhalfirinVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // When Zhalfirin Void enters the battlefield, scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1)));

        // {T}: Add {C} to you mana pool.
        this.addAbility(new ColorlessManaAbility());
    }

    private ZhalfirinVoid(final ZhalfirinVoid card) {
        super(card);
    }

    @Override
    public ZhalfirinVoid copy() {
        return new ZhalfirinVoid(this);
    }
}
