package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HallOfOracles extends CardImpl {

    public HallOfOracles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add one mana of any color.
        ActivatedAbility ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}: Put a +1/+1 counter on target creature. Activate only as a sorcery and only if you've cast an instant or sorcery spell this turn.
        ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new TapSourceCost(),
                HallOfOraclesCondition.instance, "{T}: Put a +1/+1 counter on target creature. " +
                "Activate only as a sorcery and only if you've cast an instant or sorcery spell this turn."
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTiming(TimingRule.SORCERY);
        this.addAbility(ability);
    }

    private HallOfOracles(final HallOfOracles card) {
        super(card);
    }

    @Override
    public HallOfOracles copy() {
        return new HallOfOracles(this);
    }
}

enum HallOfOraclesCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher != null
                && watcher.getSpellsCastThisTurn(source.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(spell -> spell.isInstantOrSorcery(game));
    }
}
