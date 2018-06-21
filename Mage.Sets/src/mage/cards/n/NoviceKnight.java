package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class NoviceKnight extends CardImpl {

    public NoviceKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // As long as Novice Knight is enchanted or equipped, it can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new CanAttackAsThoughItDidntHaveDefenderSourceEffect(
                                Duration.WhileOnBattlefield
                        ),
                        new OrCondition(
                                EquippedSourceCondition.instance,
                                new EnchantedSourceCondition()
                        ),
                        "As long as {this} is enchanted or equipped, "
                        + "it can attack as though it didn't have defender."
                )
        ));
    }

    public NoviceKnight(final NoviceKnight card) {
        super(card);
    }

    @Override
    public NoviceKnight copy() {
        return new NoviceKnight(this);
    }
}
