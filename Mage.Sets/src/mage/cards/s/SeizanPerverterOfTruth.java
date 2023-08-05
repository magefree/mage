

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

/**
 *
 * @author Loki
 */
public final class SeizanPerverterOfTruth extends CardImpl {

    public SeizanPerverterOfTruth (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
        
        // At the beginning of each player's upkeep, that player loses 2 life and draws two cards.
        Effect effect = new LoseLifeTargetEffect(2);
        effect.setText("that player loses 2 life");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.ANY, false);
        effect = new DrawCardTargetEffect(2);
        effect.setText("and draws two cards");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    public SeizanPerverterOfTruth (final SeizanPerverterOfTruth card) {
        super(card);
    }

    @Override
    public SeizanPerverterOfTruth copy() {
        return new SeizanPerverterOfTruth(this);
    }

}
