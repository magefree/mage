
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class DeepSeaTerror extends CardImpl {

    public DeepSeaTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deep-Sea Terror can't attack unless there are seven or more cards in your graveyard.
        Effect effect = new ConditionalRestrictionEffect(
                new CantAttackAnyPlayerSourceEffect(Duration.WhileOnBattlefield),
                new InvertCondition(new CardsInControllerGraveyardCondition(7)));
        effect.setText("{this} can't attack unless there are seven or more cards in your graveyard");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    public DeepSeaTerror(final DeepSeaTerror card) {
        super(card);
    }

    @Override
    public DeepSeaTerror copy() {
        return new DeepSeaTerror(this);
    }
}
