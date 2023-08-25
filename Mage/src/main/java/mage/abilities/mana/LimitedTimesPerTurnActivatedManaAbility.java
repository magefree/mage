
package mage.abilities.mana;

import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author LevelX2, Susucr
 */
public class LimitedTimesPerTurnActivatedManaAbility extends ActivatedManaAbilityImpl {

    public LimitedTimesPerTurnActivatedManaAbility(Zone zone, BasicManaEffect effect, Cost cost) {
        this(zone, effect, cost, 1);
    }

    public LimitedTimesPerTurnActivatedManaAbility(Zone zone, BasicManaEffect effect, Cost cost, int maxActivationPerTurn) {
        this(zone, effect, cost, maxActivationPerTurn, effect.getManaTemplate());
    }

    public LimitedTimesPerTurnActivatedManaAbility(Zone zone, AddManaOfAnyColorEffect effect, Cost cost) {
        this(zone, effect, cost, 1);
    }

    public LimitedTimesPerTurnActivatedManaAbility(Zone zone, AddManaOfAnyColorEffect effect, Cost cost, int maxActivationPerTurn) {
        this(zone, effect, cost, maxActivationPerTurn,
                new Mana(0, 0, 0, 0, 0, 0, effect.getAmount(), 0));
    }

    public LimitedTimesPerTurnActivatedManaAbility(Zone zone, ManaEffect effect, Cost cost, int maxActivationPerTurn, Mana mana) {
        this(zone, effect, cost, maxActivationPerTurn, Arrays.asList(mana));
    }

    public LimitedTimesPerTurnActivatedManaAbility(Zone zone, ManaEffect effect, Cost cost, int maxActivationPerTurn, List<Mana> mana) {
        super(zone, effect, cost);
        this.netMana.addAll(mana);
        this.maxActivationsPerTurn = maxActivationPerTurn;
    }

    private LimitedTimesPerTurnActivatedManaAbility(final LimitedTimesPerTurnActivatedManaAbility ability) {
        super(ability);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (canActivate(this.controllerId, game).canActivate()) {
            return super.activate(game, noMana);
        }
        return false;
    }

    @Override
    public String getRule() {
        String text = super.getRule() + " Activate ";
        text += maxActivationsPerTurn == 1
                ? "only once"
                : "no more than " + CardUtil.numberToText(maxActivationsPerTurn) + " times";
        text += " each turn.";
        return text;
    }

    @Override
    public LimitedTimesPerTurnActivatedManaAbility copy() {
        return new LimitedTimesPerTurnActivatedManaAbility(this);
    }

}
