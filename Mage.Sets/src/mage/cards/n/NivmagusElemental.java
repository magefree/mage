package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NivmagusElemental extends CardImpl {

    public NivmagusElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Exile an instant or sorcery spell you control: Put two +1/+1 counters on Nivmagus Elemental. (That spell won't resolve.)
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new NivmagusElementalCost()
        ));
    }

    private NivmagusElemental(final NivmagusElemental card) {
        super(card);
    }

    @Override
    public NivmagusElemental copy() {
        return new NivmagusElemental(this);
    }
}

class NivmagusElementalCost extends CostImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("an instant or sorcery spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    NivmagusElementalCost() {
        super();
        TargetSpell target = new TargetSpell(filter);
        target.setNotTarget(true);
        this.addTarget(target);
        this.text = "Exile an instant or sorcery spell you control";
    }

    private NivmagusElementalCost(NivmagusElementalCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }
        player.chooseTarget(Outcome.Exile, targets.get(0), source, game);
        Spell spell = game.getSpell(targets.getFirstTarget());
        if (spell == null) {
            return false;
        }
        String spellName = spell.getName();
        if (spell.isCopy()) {
            game.getStack().remove(spell, game);
        } else {
            player.moveCards(spell, Zone.EXILED, source, game);
        }
        paid = true;
        game.informPlayers(player.getLogName() + " exiles " + spellName + " (as costs)");
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public NivmagusElementalCost copy() {
        return new NivmagusElementalCost(this);
    }
}
