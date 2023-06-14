package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AshioksErasure extends CardImpl {

    public AshioksErasure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Ashiok's Erasure enters the battlefield, exile target spell.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AshioksErasureExileEffect());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);

        // Your opponents can't cast spells with the same name as the exiled card.
        this.addAbility(new SimpleStaticAbility(new AshioksErasureReplacementEffect()));

        // When Ashiok's Erasure leaves the battlefield, return the exiled card to its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(
                Zone.HAND
        ).setText("return the exiled card to its owner's hand"), false));
    }

    private AshioksErasure(final AshioksErasure card) {
        super(card);
    }

    @Override
    public AshioksErasure copy() {
        return new AshioksErasure(this);
    }
}

class AshioksErasureExileEffect extends OneShotEffect {

    AshioksErasureExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target spell";
    }

    private AshioksErasureExileEffect(final AshioksErasureExileEffect effect) {
        super(effect);
    }

    @Override
    public AshioksErasureExileEffect copy() {
        return new AshioksErasureExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (controller == null
                || sourceObject == null
                || spell == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        return controller.moveCardsToExile(spell, source, game, true, exileId, sourceObject.getIdName());
    }
}

class AshioksErasureReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    AshioksErasureReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Your opponents can't cast spells with the same name as the exiled card";
    }

    private AshioksErasureReplacementEffect(final AshioksErasureReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        Card card = game.getCard(event.getSourceId());
        if (sourcePermanent == null
                || card == null) {
            return false;
        }
        UUID exileZone = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());

        ExileZone exile = game.getExile().getExileZone(exileZone);
        if (exile == null) {
            // try without ZoneChangeCounter - that is useful for tokens
            exileZone = CardUtil.getCardExileZoneId(game, source);
            exile = game.getExile().getExileZone(exileZone);
        }

        if (exile == null) {
            return false;
        }

        Set<Card> cards = exile.getCards(game);
        if (cards.isEmpty()) {
            return false;
        }

        Card exiledCard = cards.iterator().next();
        if (exiledCard != null) {
            return CardUtil.haveSameNames(exiledCard, card);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public AshioksErasureReplacementEffect copy() {
        return new AshioksErasureReplacementEffect(this);
    }
}
