
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.MorphManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author fireshoes
 */
public final class WarbreakTrumpeter extends CardImpl {

    public WarbreakTrumpeter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Morph {X}{X}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{X}{X}{R}")));
        
        // When Warbreak Trumpeter is turned face up, create X 1/1 red Goblin creature tokens.
        DynamicValue morphX = MorphManacostVariableValue.instance;
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new CreateTokenEffect(new GoblinToken(), morphX)));
    }

    private WarbreakTrumpeter(final WarbreakTrumpeter card) {
        super(card);
    }

    @Override
    public WarbreakTrumpeter copy() {
        return new WarbreakTrumpeter(this);
    }
}
