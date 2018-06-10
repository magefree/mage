
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class FanaticOfMogis extends CardImpl {

    public FanaticOfMogis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Fanatic of Mogis enters the battlefield, it deals damage to each opponent equal to your devotion to red.
        Effect effect = new DamagePlayersEffect(Outcome.Damage, new DevotionCount(ColoredManaSymbol.R), TargetController.OPPONENT);
        effect.setText("it deals damage to each opponent equal to your devotion to red. (Each {R} in the mana costs of permanents you control counts towards your devotion to red.)");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));
    }

    public FanaticOfMogis(final FanaticOfMogis card) {
        super(card);
    }

    @Override
    public FanaticOfMogis copy() {
        return new FanaticOfMogis(this);
    }
}
