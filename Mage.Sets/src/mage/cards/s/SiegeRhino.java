
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SiegeRhino extends CardImpl {

    public SiegeRhino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{B}{G}");
        this.subtype.add(SubType.RHINO);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // When Siege Rhino enters the battlefield, each opponent loses 3 life and you gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeOpponentsEffect(3));
        Effect effect = new GainLifeEffect(3);
        effect.setText("and you gain 3 life");
        ability.addEffect(effect);        
        this.addAbility(ability);
    }

    private SiegeRhino(final SiegeRhino card) {
        super(card);
    }

    @Override
    public SiegeRhino copy() {
        return new SiegeRhino(this);
    }
}
