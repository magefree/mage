package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

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
        Effect effect = new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                EnchantedOrEquippedSourceCondition.instance);
        effect.setText("As long as {this} is enchanted or equipped, it can attack as though it didn't have defender");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private NoviceKnight(final NoviceKnight card) {
        super(card);
    }

    @Override
    public NoviceKnight copy() {
        return new NoviceKnight(this);
    }
}

enum EnchantedOrEquippedSourceCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            for (UUID uuid : permanent.getAttachments()) {
                Permanent attached = game.getBattlefield().getPermanent(uuid);
                if (attached != null
                        && (attached.isEnchantment(game)
                        || attached.hasSubtype(SubType.EQUIPMENT, game))) {
                            return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} is enchanted or equipped";
    }
}
