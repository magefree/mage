
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class SeraphSanctuary extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("an Angel");
    static {
        filter.add(SubType.ANGEL.getPredicate());
    }
    public SeraphSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // When Seraph Sanctuary enters the battlefield, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
        // Whenever an Angel enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), filter));
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private SeraphSanctuary(final SeraphSanctuary card) {
        super(card);
    }

    @Override
    public SeraphSanctuary copy() {
        return new SeraphSanctuary(this);
    }
}
