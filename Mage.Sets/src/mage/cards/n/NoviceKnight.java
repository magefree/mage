package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
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
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new NoviceKnightEffect()
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

class NoviceKnightEffect extends AsThoughEffectImpl {

    public NoviceKnightEffect() {
        super(AsThoughEffectType.ATTACK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as {this} is enchanted or equipped, "
                + "it can attack as though it didn't have defender.";
    }

    public NoviceKnightEffect(final NoviceKnightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NoviceKnightEffect copy() {
        return new NoviceKnightEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!objectId.equals(source.getSourceId())) {
            return false;
        }
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            for (UUID uuid : permanent.getAttachments()) {
                Permanent attached = game.getBattlefield().getPermanent(uuid);
                if (attached != null
                        && (attached.hasSubtype(SubType.EQUIPMENT, game)
                        || attached.hasSubtype(SubType.AURA, game))) {
                    return true;
                }
            }
        }
        return false;
    }
}
