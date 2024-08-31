package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SunspineLynx extends CardImpl {

    public SunspineLynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(new CantGainLifeAllEffect()));

        // Damage can't be prevented.
        this.addAbility(new SimpleStaticAbility(new DamageCantBePreventedEffect(Duration.WhileOnBattlefield)));

        // When Sunspine Lynx enters, it deals damage to each player equal to the number of nonbasic lands that player controls.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SunspineLynxEffect()));
    }

    private SunspineLynx(final SunspineLynx card) {
        super(card);
    }

    @Override
    public SunspineLynx copy() {
        return new SunspineLynx(this);
    }
}

class SunspineLynxEffect extends OneShotEffect {

    SunspineLynxEffect() {
        super(Outcome.Benefit);
        staticText = "it deals damage to each player equal to the number of nonbasic lands that player controls";
    }

    private SunspineLynxEffect(final SunspineLynxEffect effect) {
        super(effect);
    }

    @Override
    public SunspineLynxEffect copy() {
        return new SunspineLynxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> map = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_LANDS_NONBASIC,
                        source.getSourceId(), source, game
                )
                .stream()
                .collect(Collectors.toMap(
                        Controllable::getControllerId,
                        x -> 1,
                        Integer::sum
                ));
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            int amount = map.getOrDefault(playerId, 0);
            if (player != null && amount > 0) {
                player.damage(amount, source, game);
            }
        }
        return true;
    }
}
