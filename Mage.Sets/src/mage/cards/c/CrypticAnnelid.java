
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class CrypticAnnelid extends CardImpl {

    public CrypticAnnelid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.WORM);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Cryptic Annelid enters the battlefield, scry 1, then scry 2, then scry 3.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ScryEffect(1).setText("scry 1"));
        Effect effect = new ScryEffect(2);
        effect.setText(", then scry 2");
        ability.addEffect(effect);
        effect = new ScryEffect(3);
        effect.setText(", then scry 3");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private CrypticAnnelid(final CrypticAnnelid card) {
        super(card);
    }

    @Override
    public CrypticAnnelid copy() {
        return new CrypticAnnelid(this);
    }
}
