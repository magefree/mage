package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WallToken;
import mage.players.Player;

/**
 *
 * @author lagdotcom
 */
public final class BasaltGolem extends CardImpl {

    public BasaltGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Basalt Golem can't be blocked by artifact creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, Duration.WhileOnBattlefield)));

        // Whenever Basalt Golem becomes blocked by a creature, that creature's controller sacrifices it at end of combat. If the player does, they put a 0/2 colorless Wall artifact creature token with defender onto the battlefield.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new BasaltGolemEffect()), true);
        effect.setText("that creature's controller sacrifices it at end of combat. If the player does, they create a 0/2 colorless Wall artifact creature token with defender");
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(effect, false));
    }

    private BasaltGolem(final BasaltGolem card) {
        super(card);
    }

    @Override
    public BasaltGolem copy() {
        return new BasaltGolem(this);
    }
}

class BasaltGolemEffect extends OneShotEffect {
    BasaltGolemEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "that creature's controller sacrifices it. If the player does, they create a 0/2 colorless Wall artifact creature token with defender";
    }

    private BasaltGolemEffect(final BasaltGolemEffect effect) {
        super(effect);
    }

    @Override
    public BasaltGolemEffect copy() {
        return new BasaltGolemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature == null)
            return false;

        Player player = game.getPlayer(creature.getControllerId());
        if (player == null)
            return false;

        if (!creature.sacrifice(source, game))
            return false;

        return new WallToken().putOntoBattlefield(1, game, source, player.getId());
    }
}
