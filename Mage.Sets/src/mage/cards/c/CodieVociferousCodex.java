package mage.cards.c;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public final class CodieVociferousCodex extends CardImpl {

    public CodieVociferousCodex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // You can't cast permanent spells.
        this.addAbility(new SimpleStaticAbility(new CodieVociferousCodexCantCastEffect()));

        // {4}, {T}: Add {W}{U}{B}{R}{G}. When you cast your next spell this turn, exile cards from the top of your library until you exile an instant or sorcery card with lesser mana value. Until end of turn, you may cast that card without paying its mana cost. Put each other card exiled this way on the bottom of your library in a random order.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(
                1, 1, 1, 1, 1, 0, 0, 0
        ), new GenericManaCost(4));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new CodieVociferousCodexDelayedTriggeredAbility()));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CodieVociferousCodex(final CodieVociferousCodex card) {
        super(card);
    }

    @Override
    public CodieVociferousCodex copy() {
        return new CodieVociferousCodex(this);
    }
}

class CodieVociferousCodexCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    CodieVociferousCodexCantCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "you can't cast permanent spells";
    }

    private CodieVociferousCodexCantCastEffect(final CodieVociferousCodexCantCastEffect effect) {
        super(effect);
    }

    @Override
    public CodieVociferousCodexCantCastEffect copy() {
        return new CodieVociferousCodexCantCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        Card card = game.getCard(event.getSourceId());
        return card != null && card.isPermanent(game);
    }
}

class CodieVociferousCodexDelayedTriggeredAbility extends DelayedTriggeredAbility {

    CodieVociferousCodexDelayedTriggeredAbility() {
        super(new CodieVociferousCodexEffect(), Duration.EndOfTurn, true, false);
    }

    private CodieVociferousCodexDelayedTriggeredAbility(final CodieVociferousCodexDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        this.getEffects().setValue("spellCast", spell);
        return true;
    }

    @Override
    public CodieVociferousCodexDelayedTriggeredAbility copy() {
        return new CodieVociferousCodexDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you cast your next spell this turn, exile cards from the top of your library "
                + "until you exile an instant or sorcery card with lesser mana value. Until end of turn, "
                + "you may cast that card without paying its mana cost. Put each other card exiled this way "
                + "on the bottom of your library in a random order.";
    }
}

class CodieVociferousCodexEffect extends OneShotEffect {

    CodieVociferousCodexEffect() {
        super(Outcome.Benefit);
    }

    private CodieVociferousCodexEffect(final CodieVociferousCodexEffect effect) {
        super(effect);
    }

    @Override
    public CodieVociferousCodexEffect copy() {
        return new CodieVociferousCodexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (controller == null
                || spell == null) {
            return false;
        }
        Cards toExile = new CardsImpl();
        Card toCast = null;
        for (Card card : controller.getLibrary().getCards(game)) {
            toExile.add(card);
            if (card.isInstantOrSorcery(game)
                    && card.getManaValue() < spell.getManaValue()) {
                toCast = card;
                break;
            }
        }
        if (toCast == null) {
            controller.moveCards(toExile, Zone.EXILED, source, game);
            controller.putCardsOnBottomOfLibrary(toExile, game, source, false);
            return true;
        }
        controller.moveCards(toCast, Zone.EXILED, source, game);
        PlayFromNotOwnHandZoneTargetEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, TargetController.YOU, Duration.EndOfTurn, true);
        effect.setTargetPointer(new FixedTarget(toCast.getId(), game));
        game.addEffect(effect, source);
        toExile.remove(toCast);
        controller.putCardsOnBottomOfLibrary(toExile, game, source, false);
        return true;
    }
}
