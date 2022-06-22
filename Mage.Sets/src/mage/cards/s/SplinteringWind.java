package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SplinterToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class SplinteringWind extends CardImpl {

    public SplinteringWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // {2}{G}: Splintering Wind deals 1 damage to target creature. Create a 1/1 green Splinter creature token. It has flying and “Cumulative upkeep {G}.” When it leaves the battlefield, it deals 1 damage to you and each creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{2}{G}"));
        ability.addEffect(new SplinteringWindCreateTokenEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SplinteringWind(final SplinteringWind card) {
        super(card);
    }

    @Override
    public SplinteringWind copy() {
        return new SplinteringWind(this);
    }
}

class SplinteringWindCreateTokenEffect extends OneShotEffect {

    public SplinteringWindCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 1/1 green Splinter creature token. It has flying and \"Cumulative upkeep {G}.\" When it leaves the battlefield, it deals 1 damage to you and each creature you control";
    }

    public SplinteringWindCreateTokenEffect(final SplinteringWindCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourceController = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceController != null && sourceObject != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new SplinterToken());
            effect.apply(game, source);
            game.getState().setValue(source.getSourceId() + "_token", effect.getLastAddedTokenIds());
            for (UUID addedTokenId : effect.getLastAddedTokenIds()) {
                game.addDelayedTriggeredAbility(new SplinteringWindDelayedTriggeredAbility(addedTokenId), source);
            }
            return true;
        }
        return false;
    }

    @Override
    public SplinteringWindCreateTokenEffect copy() {
        return new SplinteringWindCreateTokenEffect(this);
    }
}

class SplinteringWindDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID tokenId;

    SplinteringWindDelayedTriggeredAbility(UUID tokenId) {
        super(new DamageControllerEffect(1), Duration.OneUse);
        this.addEffect(new DamageAllEffect(1, new FilterControlledCreaturePermanent()));
        this.tokenId = tokenId;
    }

    SplinteringWindDelayedTriggeredAbility(final SplinteringWindDelayedTriggeredAbility ability) {
        super(ability);
        this.tokenId = ability.tokenId;
    }

    @Override
    public SplinteringWindDelayedTriggeredAbility copy() {
        return new SplinteringWindDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(tokenId)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When it leaves the battlefield, it deals 1 damage to you and each creature you control.";
    }
}
