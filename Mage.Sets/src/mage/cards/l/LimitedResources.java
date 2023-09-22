package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class LimitedResources extends CardImpl {

    public LimitedResources(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // When Limited Resources enters the battlefield, each player chooses five lands they control and sacrifices the rest.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LimitedResourcesEffect(), false));

        // Players can't play lands as long as ten or more lands are on the battlefield.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousRuleModifyingEffect(
                        new CantPlayLandEffect(),
                        new PermanentsOnTheBattlefieldCondition(
                                new FilterLandPermanent(),
                                ComparisonType.MORE_THAN, 9, false))));

    }

    private LimitedResources(final LimitedResources card) {
        super(card);
    }

    @Override
    public LimitedResources copy() {
        return new LimitedResources(this);
    }
}

class LimitedResourcesEffect extends OneShotEffect {

    public LimitedResourcesEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player chooses five lands they control and sacrifices the rest";
    }

    private LimitedResourcesEffect(final LimitedResourcesEffect effect) {
        super(effect);
    }

    @Override
    public LimitedResourcesEffect copy() {
        return new LimitedResourcesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getPlayersInRange(source.getControllerId(), game).forEach((playerId) -> {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int lands = game.getBattlefield().countAll(new FilterControlledLandPermanent(), playerId, game);
                TargetLandPermanent target = new TargetLandPermanent(Integer.min(5, lands));
                target.withNotTarget(true);
                target.setRequired(true);
                player.chooseTarget(outcome.Benefit, target, source, game);
                game.getBattlefield().getAllActivePermanents(new FilterControlledLandPermanent(), playerId, game).stream().filter((land) -> (!target.getTargets().contains(land.getId()))).forEachOrdered((land) -> {
                    land.sacrifice(source, game);
                });
            }
        });
        return true;
    }
}

class CantPlayLandEffect extends ContinuousRuleModifyingEffectImpl {

    public CantPlayLandEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Players can't play lands as long as ten or more lands are on the battlefield";
    }

    private CantPlayLandEffect(final CantPlayLandEffect effect) {
        super(effect);
    }

    @Override
    public CantPlayLandEffect copy() {
        return new CantPlayLandEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}
