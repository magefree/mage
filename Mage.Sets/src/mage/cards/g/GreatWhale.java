
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class GreatWhale extends CardImpl {

    public GreatWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Great Whale enters the battlefield, untap up to seven lands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapLandsEffect(7)));
    }

    private GreatWhale(final GreatWhale card) {
        super(card);
    }

    @Override
    public GreatWhale copy() {
        return new GreatWhale(this);
    }
}
