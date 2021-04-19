package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AvatarOfMight extends CardImpl {

    private static final Condition condition = new AvatarOfMightCondition();

    public AvatarOfMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // If an opponent controls at least four more creatures than you, Avatar of Might costs {6} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(6, condition)
                .setText("If an opponent controls at least four more creatures than you, this spell costs {6} less to cast"))
                .addHint(new ConditionHint(condition, "Opponent controls at least four more creatures than you"))
        );

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private AvatarOfMight(final AvatarOfMight card) {
        super(card);
    }

    @Override
    public AvatarOfMight copy() {
        return new AvatarOfMight(this);
    }
}

class AvatarOfMightCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        int creatures = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent != null && game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, opponent.getId(), game) >= creatures + 4) {
                return true;
            }
        }
        return false;
    }
}
