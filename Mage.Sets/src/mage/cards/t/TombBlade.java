package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TombBlade extends CardImpl {

    public TombBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Tomb Blade deals combat damage to a player, that player loses life equal to the number of creatures they control unless they sacrifice a creature.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new TombBladeEffect(), false, true
        ));

        // Unearth {6}{B}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{6}{B}{B}")));
    }

    private TombBlade(final TombBlade card) {
        super(card);
    }

    @Override
    public TombBlade copy() {
        return new TombBlade(this);
    }
}

class TombBladeEffect extends OneShotEffect {

    TombBladeEffect() {
        super(Outcome.Benefit);
        staticText = "that player loses life equal to the number of " +
                "creatures they control unless they sacrifice a creature";
    }

    private TombBladeEffect(final TombBladeEffect effect) {
        super(effect);
    }

    @Override
    public TombBladeEffect copy() {
        return new TombBladeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int creatureCount = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                player.getId(), source, game
        );
        Cost cost = new SacrificeTargetCost(new TargetControlledCreaturePermanent());
        if (cost.canPay(source, source, player.getId(), game)
                && player.chooseUse(outcome, "Sacrifice a creature?",
                "If you don't you lose " + creatureCount + " life",
                "Yes", "No", source, game)
                && cost.pay(source, game, source, player.getId(), true)) {
            return true;
        }
        return player.loseLife(creatureCount, game, source, false) > 0;
    }
}
