package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class VraskaRegalGorgon extends CardImpl {

    public VraskaRegalGorgon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{5}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRASKA);
        this.setStartingLoyalty(5);

        // +2: Put a +1/+1 counter on up to one target creature. That creature gains menace until end of turn.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ).setText("Put a +1/+1 counter on up to one target creature"), 2);
        ability.addEffect(new GainAbilityTargetEffect(
                new MenaceAbility(), Duration.EndOfTurn
        ).setText("That creature gains menace until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -3: Destroy target creature.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -10: For each creature card in your graveyard, put a +1/+1 counter on each creature you control.
        this.addAbility(new LoyaltyAbility(new VraskaRegalGorgonEffect(), -10));
    }

    private VraskaRegalGorgon(final VraskaRegalGorgon card) {
        super(card);
    }

    @Override
    public VraskaRegalGorgon copy() {
        return new VraskaRegalGorgon(this);
    }
}

class VraskaRegalGorgonEffect extends OneShotEffect {

    public VraskaRegalGorgonEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each creature card in your graveyard, "
                + "put a +1/+1 counter on each creature you control.";
    }

    public VraskaRegalGorgonEffect(final VraskaRegalGorgonEffect effect) {
        super(effect);
    }

    @Override
    public VraskaRegalGorgonEffect copy() {
        return new VraskaRegalGorgonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = player.getGraveyard().count(
                StaticFilters.FILTER_CARD_CREATURE, game
        );
        return new AddCountersAllEffect(
                CounterType.P1P1.createInstance(count),
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).apply(game, source);
    }
}
