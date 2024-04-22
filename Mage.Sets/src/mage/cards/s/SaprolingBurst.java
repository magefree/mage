
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SaprolingBurstToken;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public final class SaprolingBurst extends CardImpl {

    public SaprolingBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // Fading 7
        this.addAbility(new FadingAbility(7, this));

        // Remove a fade counter from Saproling Burst: create a green Saproling creature token. It has "This creature's power and toughness are each equal to the number of fade counters on Saproling Burst."
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SaprolingBurstCreateTokenEffect(), new RemoveCountersSourceCost(CounterType.FADE.createInstance())));

        // When Saproling Burst leaves the battlefield, destroy all tokens created with Saproling Burst. They can't be regenerated.
        this.addAbility(new SaprolingBurstLeavesBattlefieldTriggeredAbility());
    }

    private SaprolingBurst(final SaprolingBurst card) {
        super(card);
    }

    @Override
    public SaprolingBurst copy() {
        return new SaprolingBurst(this);
    }
}

class SaprolingBurstCreateTokenEffect extends OneShotEffect {

    SaprolingBurstCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a green Saproling creature token. It has \"This creature's power and toughness are each equal to the number of fade counters on {this}.\"";
    }

    private SaprolingBurstCreateTokenEffect(final SaprolingBurstCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public SaprolingBurstCreateTokenEffect copy() {
        return new SaprolingBurstCreateTokenEffect(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean apply(Game game, Ability source) {
        Token token = new SaprolingBurstToken(new MageObjectReference(source.getSourceObject(game), game));
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Object object = game.getState().getValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game));
            Set<UUID> tokensCreated;
            if (object != null) {
                tokensCreated = (Set<UUID>) object;
            } else {
                tokensCreated = new HashSet<>();
            }
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                tokensCreated.add(tokenId);
            }
            game.getState().setValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game), tokensCreated);
        }
        return true;
    }
}

class SaprolingBurstLeavesBattlefieldTriggeredAbility extends ZoneChangeTriggeredAbility {

    SaprolingBurstLeavesBattlefieldTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, new SaprolingBurstDestroyEffect(), "When {this} leaves the battlefield, ", false);
    }

    private SaprolingBurstLeavesBattlefieldTriggeredAbility(final SaprolingBurstLeavesBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            for (Effect effect : this.getEffects()) {
                if (effect instanceof SaprolingBurstDestroyEffect) {
                    ((SaprolingBurstDestroyEffect) effect).setCardZoneString(CardUtil.getCardZoneString("_tokensCreated", this.getSourceId(), game, true));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SaprolingBurstLeavesBattlefieldTriggeredAbility copy() {
        return new SaprolingBurstLeavesBattlefieldTriggeredAbility(this);
    }
}

class SaprolingBurstDestroyEffect extends OneShotEffect {

    private String cardZoneString;

    SaprolingBurstDestroyEffect() {
        super(Outcome.Benefit);
        this.staticText = "destroy all tokens created with {this}. They can't be regenerated";
    }

    private SaprolingBurstDestroyEffect(final SaprolingBurstDestroyEffect effect) {
        super(effect);
        this.cardZoneString = effect.cardZoneString;
    }

    @Override
    public SaprolingBurstDestroyEffect copy() {
        return new SaprolingBurstDestroyEffect(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue(cardZoneString);
        if (object != null) {
            Set<UUID> tokensCreated = (Set<UUID>) object;
            for (UUID tokenId : tokensCreated) {
                Permanent token = game.getPermanent(tokenId);
                if (token != null) {
                    token.destroy(source, game, true);
                }
            }
        }
        return true;
    }

    public void setCardZoneString(String cardZoneString) {
        this.cardZoneString = cardZoneString;
    }
}
