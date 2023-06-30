
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ActivateOncePerTurnManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class RamosDragonEngine extends CardImpl {

    public RamosDragonEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a spell, put a +1/+1 counter on Ramos, Dragon Engine for each of that spell's colors.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RamosDragonEngineAddCountersEffect(), StaticFilters.FILTER_SPELL_A, false, true));

        // Remove five +1/+1 counters from Ramos: Add {W}{W}{U}{U}{B}{B}{R}{R}{G}{G}. Activate this ability only once each turn.        
        Ability ability = new ActivateOncePerTurnManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(new Mana(2, 2, 2, 2, 2, 0, 0, 0)), new RemoveCountersSourceCost(CounterType.P1P1.createInstance(5)));
        this.addAbility(ability);
    }

    private RamosDragonEngine(final RamosDragonEngine card) {
        super(card);
    }

    @Override
    public RamosDragonEngine copy() {
        return new RamosDragonEngine(this);
    }
}

class RamosDragonEngineAddCountersEffect extends OneShotEffect {

    public RamosDragonEngineAddCountersEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on {this} for each of that spell's colors";
    }

    public RamosDragonEngineAddCountersEffect(final RamosDragonEngineAddCountersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (you != null && permanent != null) {
            Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
            if (spell != null) {
                int amount = 0;
                if (spell.getColor(game).isWhite()) {
                    ++amount;
                }
                if (spell.getColor(game).isBlue()) {
                    ++amount;
                }
                if (spell.getColor(game).isBlack()) {
                    ++amount;
                }
                if (spell.getColor(game).isRed()) {
                    ++amount;
                }
                if (spell.getColor(game).isGreen()) {
                    ++amount;
                }
                if (amount > 0) {
                    permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public RamosDragonEngineAddCountersEffect copy() {
        return new RamosDragonEngineAddCountersEffect(this);
    }
}
