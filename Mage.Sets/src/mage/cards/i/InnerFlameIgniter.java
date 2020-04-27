package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InnerFlameIgniter extends CardImpl {

    public InnerFlameIgniter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{R}: Creatures you control get +1/+0 until end of turn. If this is the third time this ability has resolved this turn, creatures you control gain first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{2}{R}")
        );
        ability.addEffect(new InnerFlameIgniterEffect());
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private InnerFlameIgniter(final InnerFlameIgniter card) {
        super(card);
    }

    @Override
    public InnerFlameIgniter copy() {
        return new InnerFlameIgniter(this);
    }
}

class InnerFlameIgniterEffect extends OneShotEffect {

    InnerFlameIgniterEffect() {
        super(Outcome.AddAbility);
        this.staticText = "If this is the third time this ability has resolved this turn, " +
                "creatures you control gain first strike until end of turn";
    }

    private InnerFlameIgniterEffect(final InnerFlameIgniterEffect effect) {
        super(effect);
    }

    @Override
    public InnerFlameIgniterEffect copy() {
        return new InnerFlameIgniterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        AbilityResolvedWatcher watcher = game.getState().getWatcher(AbilityResolvedWatcher.class);
        if (controller == null
                || watcher == null
                || !watcher.checkActivations(source, game)) {
            return false;
        }
        game.addEffect(new GainAbilityControlledEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ), source);
        return true;
    }
}
