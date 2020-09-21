package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HagraMauling extends CardImpl {

    private static final Hint hint = new ConditionHint(
            HagraMaulingCondition.instance, "An opponent controls no basic lands"
    );

    public HagraMauling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.h.HagraBroodpit.class;

        // This spell costs {1} less to cast if an opponent controls no basic lands.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, HagraMaulingCondition.instance).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);
    }

    private HagraMauling(final HagraMauling card) {
        super(card);
    }

    @Override
    public HagraMauling copy() {
        return new HagraMauling(this);
    }
}

enum HagraMaulingCondition implements Condition {
    instance;

    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null && game.getBattlefield().count(filter, source.getSourceId(), playerId, game) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "an opponent controls no basic lands";
    }
}
