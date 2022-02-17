
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author Addictiveme
 */
public final class TouchTheSpiritRealm extends CardImpl {

    private static final FilterPermanent filter = StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE;

    public TouchTheSpiritRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Touch the Spirit Realm enters the battlefield, exile up to one target artifact or creature until
        // Touch the Spirit Realm leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect(filter.getMessage())
        		.setText("exile up to one target " + filter.getMessage()
        		+ " until {this} leaves the battlefield"));
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
        
        // Channel - {1}{W}, Discard Touch the Spirit Realm: Exile target artifact or creature.
        // Return it to the battlefield under its owner's control at the beginning of the next end step.
        Ability channelAbility = new ChannelAbility("{1}{W}", new TouchTheSpiritRealmEffect());
        channelAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(channelAbility);
    }

    private TouchTheSpiritRealm(final TouchTheSpiritRealm card) {
        super(card);
    }

    @Override
    public TouchTheSpiritRealm copy() {
        return new TouchTheSpiritRealm(this);
    }
}

class TouchTheSpiritRealmEffect extends OneShotEffect {

    public TouchTheSpiritRealmEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target artifact or creature. Return it to the battlefield under its owner's control at the beginning of the next end step";
    }

    private TouchTheSpiritRealmEffect(final TouchTheSpiritRealmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCardsToExile(permanent, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        ReturnToBattlefieldUnderOwnerControlTargetEffect returnEffect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, true);
        returnEffect.setTargetPointer(new FixedTarget(card,game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(returnEffect), source);
        return true;
    }
    
    @Override
    public TouchTheSpiritRealmEffect copy() {
        return new TouchTheSpiritRealmEffect(this);
    }
}