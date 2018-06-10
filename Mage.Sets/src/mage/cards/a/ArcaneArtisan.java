
package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class ArcaneArtisan extends CardImpl {

    public ArcaneArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {2}{U}, {T}: Target player draws a card, then exiles a card from their hand. If a creature card is exiled this way, that player creates a token that's a copy of that card.
        Ability ability = new SimpleActivatedAbility(new ArcaneArtisanCreateTokenEffect(), new ManaCostsImpl("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // When Arcane Artisan leaves the battlefield, exile all tokens created with it at the beginning of the next end step.
        this.addAbility(new ArcaneArtisanLeavesBattlefieldTriggeredAbility());
    }

    public ArcaneArtisan(final ArcaneArtisan card) {
        super(card);
    }

    @Override
    public ArcaneArtisan copy() {
        return new ArcaneArtisan(this);
    }
}

class ArcaneArtisanCreateTokenEffect extends OneShotEffect {

    ArcaneArtisanCreateTokenEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player draws a card, then exiles a card from their hand. "
                + "If a creature card is exiled this way, that player creates a token that's a copy of that card.";
    }

    ArcaneArtisanCreateTokenEffect(final ArcaneArtisanCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public ArcaneArtisanCreateTokenEffect copy() {
        return new ArcaneArtisanCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        player.drawCards(1, game);
        Target target = new TargetCardInHand(1, new FilterCard());
        if (!player.chooseTarget(Outcome.Exile, target, source, game)) {
            return false;
        }
        Card card = game.getCard(target.getFirstTarget());
        player.moveCards(card, Zone.EXILED, source, game);
        if (!card.isCreature()) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(player.getId());
        effect.setTargetPointer(new FixedTarget(card.getId(), game));
        effect.apply(game, source);
        Object object = game.getState().getValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game));
        Set<UUID> tokensCreated;
        if (object != null) {
            tokensCreated = (Set<UUID>) object;
        } else {
            tokensCreated = new HashSet<>();
        }
        for (Permanent perm : effect.getAddedPermanent()) {
            if (perm != null) {
                tokensCreated.add(perm.getId());
            }
        }
        game.getState().setValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game), tokensCreated);
        return true;
    }
}

class ArcaneArtisanLeavesBattlefieldTriggeredAbility extends ZoneChangeTriggeredAbility {

    ArcaneArtisanLeavesBattlefieldTriggeredAbility() {
        super(Zone.BATTLEFIELD, null,
                new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ArcaneArtisanExileEffect())),
                "", false
        );
    }

    ArcaneArtisanLeavesBattlefieldTriggeredAbility(ArcaneArtisanLeavesBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArcaneArtisanLeavesBattlefieldTriggeredAbility copy() {
        return new ArcaneArtisanLeavesBattlefieldTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this} leaves the battlefield, exile all tokens created with it at the beginning of the next end step";
    }
}

class ArcaneArtisanExileEffect extends OneShotEffect {

    ArcaneArtisanExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile all tokens created with {this}.";
    }

    ArcaneArtisanExileEffect(final ArcaneArtisanExileEffect effect) {
        super(effect);
    }

    @Override
    public ArcaneArtisanExileEffect copy() {
        return new ArcaneArtisanExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game, true));
        if (object != null) {
            Set<UUID> tokensCreated = (Set<UUID>) object;
            for (UUID tokenId : tokensCreated) {
                Permanent token = game.getPermanent(tokenId);
                if (token != null) {
                    token.destroy(source.getSourceId(), game, true);
                }
            }
        }
        return true;
    }
}
