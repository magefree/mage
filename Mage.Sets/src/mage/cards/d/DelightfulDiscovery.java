package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DelightfulDiscovery extends CardImpl {

    public DelightfulDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // This spell costs {1} less to cast for each spell your opponents have cast this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionForEachSourceEffect(
                        1, DelightfulDiscoveryValue.instance
                ).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true).addHint(DelightfulDiscoveryValue.getHint()));

        // Scry 2, then draw two cards.
        this.getSpellAbility().addEffect(new ScryEffect(2, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
    }

    private DelightfulDiscovery(final DelightfulDiscovery card) {
        super(card);
    }

    @Override
    public DelightfulDiscovery copy() {
        return new DelightfulDiscovery(this);
    }
}

enum DelightfulDiscoveryValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Spells your opponents cast this turn", instance);

    static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getOpponents(sourceAbility.getControllerId())
                .stream()
                .mapToInt(game.getState().getWatcher(SpellsCastWatcher.class)::getCount)
                .sum();
    }

    @Override
    public DelightfulDiscoveryValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "spell your opponents have cast this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}
