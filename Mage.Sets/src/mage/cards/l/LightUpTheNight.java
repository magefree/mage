package mage.cards.l;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.common.RemoveVariableCountersTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author weirddan455
 */
public final class LightUpTheNight extends CardImpl {

    public LightUpTheNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Light Up the Night deals X damage to any target. It deals X plus 1 damage instead if that target is a creature or planeswalker.
        this.getSpellAbility().addEffect(new LightUpTheNightEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Flashback—{3}{R}, Remove X loyalty counters from among planeswalkers you control. If you cast this spell this way, X can't be 0.
        Ability ability = new FlashbackAbility(this, new ManaCostsImpl<>("{3}{R}"));
        ability.addCost(new RemoveVariableCountersTargetCost(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER, CounterType.LOYALTY, "X", 1,
                "Remove X loyalty counters from among planeswalkers you control. If you cast this spell this way, X can't be 0"
        ));
        this.addAbility(ability);
    }

    private LightUpTheNight(final LightUpTheNight card) {
        super(card);
    }

    @Override
    public LightUpTheNight copy() {
        return new LightUpTheNight(this);
    }
}

class LightUpTheNightEffect extends OneShotEffect {

    public LightUpTheNightEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to any target. It deals X plus 1 damage instead if that target is a creature or planeswalker";
    }

    private LightUpTheNightEffect(final LightUpTheNightEffect effect) {
        super(effect);
    }

    @Override
    public LightUpTheNightEffect copy() {
        return new LightUpTheNightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Normal cast
        int damage = source.getManaCostsToPay().getX();
        // Flashback cast
        damage += GetXValue.instance.calculate(game, source, this);
        UUID targetId = source.getFirstTarget();
        Player player = game.getPlayer(targetId);
        if (player != null) {
            player.damage(damage, source.getSourceId(), source, game);
            return true;
        }
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            permanent.damage(damage + 1, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }
}
