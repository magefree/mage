package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantAttackSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DeepSeaTerror extends CardImpl {

    private static final Condition condition = new InvertCondition(ThresholdCondition.instance);

    public DeepSeaTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deep-Sea Terror can't attack unless there are seven or more cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantAttackSourceEffect(Duration.WhileOnBattlefield), condition
        ).setText("{this} can't attack unless there are seven or more cards in your graveyard")));
    }

    private DeepSeaTerror(final DeepSeaTerror card) {
        super(card);
    }

    @Override
    public DeepSeaTerror copy() {
        return new DeepSeaTerror(this);
    }
}
