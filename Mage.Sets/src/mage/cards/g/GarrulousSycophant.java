
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class GarrulousSycophant extends CardImpl {

    public GarrulousSycophant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, if you're the monarch, each opponent loses 1 life and you gain 1 life.
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(1),
                TargetController.YOU, MonarchIsSourceControllerCondition.instance, false);
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        ability.addHint(MonarchHint.instance);
        this.addAbility(ability);

    }

    private GarrulousSycophant(final GarrulousSycophant card) {
        super(card);
    }

    @Override
    public GarrulousSycophant copy() {
        return new GarrulousSycophant(this);
    }
}
