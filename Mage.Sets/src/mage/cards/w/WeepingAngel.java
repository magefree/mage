package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagePermanentEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jimga150
 */
public final class WeepingAngel extends CardImpl {

    public WeepingAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}{B}");
        
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever an opponent casts a creature spell, Weeping Angel isn't a creature until end of turn.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new WeepingAngelMarbleizeEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));

        // If Weeping Angel would deal combat damage to a creature, prevent that damage and that creature's owner shuffles it into their library.
        this.addAbility(new SimpleStaticAbility(new WeepingAngelDamageEffect()));
    }

    private WeepingAngel(final WeepingAngel card) {
        super(card);
    }

    @Override
    public WeepingAngel copy() {
        return new WeepingAngel(this);
    }
}

// Adapted from LoseCreatureTypeSourceEffect
class WeepingAngelMarbleizeEffect extends ContinuousEffectImpl {
    
    WeepingAngelMarbleizeEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        staticText = "{this} isn't a creature until end of turn.";
    }

    private WeepingAngelMarbleizeEffect(final WeepingAngelMarbleizeEffect effect) {
        super(effect);
    }

    @Override
    public WeepingAngelMarbleizeEffect copy() {
        return new WeepingAngelMarbleizeEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (duration.isOnlyValidIfNoZoneChange()) {
            // If source permanent is no longer onto battlefield discard the effect
            if (source.getSourcePermanentIfItStillExists(game) == null) {
                discard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        permanent.removeCardType(game, CardType.CREATURE);
        if (!permanent.isTribal(game)) {
            permanent.removeAllCreatureTypes(game);
        }
        if (permanent.isAttacking() || permanent.getBlocking() > 0) {
            permanent.removeFromCombat(game);
        }
        return true;
    }
}

// Based on PreventDamageAndRemoveCountersEffect
class WeepingAngelDamageEffect extends PreventionEffectImpl {

    WeepingAngelDamageEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, true, false);
        staticText = "If {this} would deal combat damage to a creature, " +
                "prevent that damage and that creature's owner shuffles it into their library.";
    }

    private WeepingAngelDamageEffect(final WeepingAngelDamageEffect effect) {
        super(effect);
    }

    @Override
    public WeepingAngelDamageEffect copy() {
        return new WeepingAngelDamageEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        game.preventDamage(event, source, game, Integer.MAX_VALUE);
        Card card = game.getPermanent(event.getTargetId());
        if (card == null) {
            return false;
        }
        Player owner = game.getPlayer(card.getOwnerId());
        if (owner != null) {
            owner.shuffleCardsToLibrary(card, game, source);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game)){
            return false;
        }
        return event.getSourceId().equals(source.getSourceId()) && ((DamagePermanentEvent) event).isCombatDamage();
    }
}
