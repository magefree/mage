package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrawlingBarrens extends CardImpl {

    public CrawlingBarrens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}: Put two +1/+1 counters on Crawling Barrens. Then you may have it become a 0/0 Elemental creature until end of turn. It's still a land.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new GenericManaCost(4)
        );
        ability.addEffect(new CrawlingBarrensEffect());
        this.addAbility(ability);
    }

    private CrawlingBarrens(final CrawlingBarrens card) {
        super(card);
    }

    @Override
    public CrawlingBarrens copy() {
        return new CrawlingBarrens(this);
    }
}

class CrawlingBarrensEffect extends OneShotEffect {

    CrawlingBarrensEffect() {
        super(Outcome.Benefit);
        staticText = "Then you may have it become a 0/0 Elemental creature until end of turn. It's still a land.";
    }

    private CrawlingBarrensEffect(final CrawlingBarrensEffect effect) {
        super(effect);
    }

    @Override
    public CrawlingBarrensEffect copy() {
        return new CrawlingBarrensEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(outcome, "Have this land become a 0/0 creature until end of turn?", source, game)) {
            return false;
        }
        game.addEffect(new BecomesCreatureSourceEffect(new CreatureToken(
                0, 0, "0/0 Elemental creature"
        ).withSubType(SubType.ELEMENTAL), CardType.LAND, Duration.EndOfTurn), source);
        return true;
    }
}
