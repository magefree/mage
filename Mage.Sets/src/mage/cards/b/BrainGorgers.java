
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BrainGorgers extends CardImpl {

    public BrainGorgers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When you cast Brain Gorgers, any player may sacrifice a creature. If a player does, counter Brain Gorgers.
        this.addAbility(new CastSourceTriggeredAbility(new BrainGorgersCounterSourceEffect()));

        // Madness {1}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private BrainGorgers(final BrainGorgers card) {
        super(card);
    }

    @Override
    public BrainGorgers copy() {
        return new BrainGorgers(this);
    }
}

class BrainGorgersCounterSourceEffect extends OneShotEffect {

    public BrainGorgersCounterSourceEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "any player may sacrifice a creature. If a player does, counter {this}";
    }

    public BrainGorgersCounterSourceEffect(final BrainGorgersCounterSourceEffect effect) {
        super(effect);
    }

    @Override
    public BrainGorgersCounterSourceEffect copy() {
        return new BrainGorgersCounterSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            SacrificeTargetCost cost = new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
            for (UUID playerId : game.getState().getPlayerList(source.getControllerId())) {
                cost.clearPaid();
                Player player = game.getPlayer(playerId);
                if (player != null && cost.canPay(source, source, player.getId(), game)
                        && player.chooseUse(outcome, "Sacrifice a creature to counter " + sourceObject.getIdName() + '?', source, game)) {
                    if (cost.pay(source, game, source, player.getId(), false, null)) {
                        game.informPlayers(player.getLogName() + " sacrifices a creature to counter " + sourceObject.getIdName() + '.');
                        Spell spell = game.getStack().getSpell(source.getSourceId());
                        if (spell != null) {
                            game.getStack().counter(spell.getId(), source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;

    }
}
