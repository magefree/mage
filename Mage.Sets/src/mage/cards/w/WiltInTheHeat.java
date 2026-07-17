package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.CardsLeftGraveyardWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WiltInTheHeat extends CardImpl {

    public WiltInTheHeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{W}");

        // This spell costs {2} less to cast if one or more cards left your graveyard this turn.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2, WiltInTheHeatCondition.instance));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability, new CardsLeftGraveyardWatcher());

        // Wilt in the Heat deals 5 damage to target creature. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WiltInTheHeat(final WiltInTheHeat card) {
        super(card);
    }

    @Override
    public WiltInTheHeat copy() {
        return new WiltInTheHeat(this);
    }
}

enum WiltInTheHeatCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CardsLeftGraveyardWatcher watcher =
                game.getState().getWatcher(CardsLeftGraveyardWatcher.class);

        return watcher != null
                && !watcher.getCardsThatLeftGraveyard(source.getControllerId(), game).isEmpty();
    }

    @Override
    public String toString() {
        return "if one or more cards left your graveyard this turn";
    }
}
