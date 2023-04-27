package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class AsmodeusTheArchfiend extends CardImpl {

    public AsmodeusTheArchfiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Binding Contract — If you would draw a card, exile the top card of your library face down instead.
        this.addAbility(new SimpleStaticAbility(new AsmodeusTheArchfiendReplacementEffect()).withFlavorWord("Binding Contract"));

        // {B}{B}{B}: Draw seven cards.
        this.addAbility(new SimpleActivatedAbility(new DrawCardSourceControllerEffect(7), new ManaCostsImpl<>("{B}{B}{B}")));

        // {B}: Return all cards exiled with Asmodeus the Archfiend to their owner's hand and you lose that much life.
        this.addAbility(new AsmodeusTheArchfiendReturnAbility());
    }

    private AsmodeusTheArchfiend(final AsmodeusTheArchfiend card) {
        super(card);
    }

    @Override
    public AsmodeusTheArchfiend copy() {
        return new AsmodeusTheArchfiend(this);
    }
}

class AsmodeusTheArchfiendReplacementEffect extends ReplacementEffectImpl {

    public AsmodeusTheArchfiendReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        this.staticText = "If you would draw a card, exile the top card of your library face down instead";
    }

    private AsmodeusTheArchfiendReplacementEffect(final AsmodeusTheArchfiendReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AsmodeusTheArchfiendReplacementEffect copy() {
        return new AsmodeusTheArchfiendReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (controller != null && sourcePermanent != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                UUID exileZoneId = CardUtil.getExileZoneId(game, sourcePermanent.getId(), sourcePermanent.getZoneChangeCounter(game));
                String exileName = CardUtil.createObjectRealtedWindowTitle(source, game, null);
                controller.moveCardsToExile(card, source, game, false, exileZoneId, exileName);
                card.setFaceDown(true, game);
            }
        }
        return true;
    }
}

class AsmodeusTheArchfiendReturnAbility extends ActivatedAbilityImpl {

    public AsmodeusTheArchfiendReturnAbility() {
        super(Zone.BATTLEFIELD, new AsmodeusTheArchfiendReturnEffect(), new ManaCostsImpl<>("{B}"));
    }

    private AsmodeusTheArchfiendReturnAbility(final AsmodeusTheArchfiendReturnAbility ability) {
        super(ability);
    }

    @Override
    public AsmodeusTheArchfiendReturnAbility copy() {
        return new AsmodeusTheArchfiendReturnAbility(this);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            Permanent sourcePermanent = this.getSourcePermanentIfItStillExists(game);
            if (sourcePermanent != null) {
                // Needed to save zcc on activation so it still works if the permanent changes zones in response to the ability being activated.
                this.getEffects().setValue("exileZoneId", CardUtil.getExileZoneId(game, sourcePermanent.getId(), sourcePermanent.getZoneChangeCounter(game)));
            }
            return true;
        }
        return false;
    }
}

class AsmodeusTheArchfiendReturnEffect extends OneShotEffect {

    public AsmodeusTheArchfiendReturnEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Return all cards exiled with {this} to their owner's hand and you lose that much life";
    }

    private AsmodeusTheArchfiendReturnEffect(final AsmodeusTheArchfiendReturnEffect effect) {
        super(effect);
    }

    @Override
    public AsmodeusTheArchfiendReturnEffect copy() {
        return new AsmodeusTheArchfiendReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID exileZoneId = (UUID) this.getValue("exileZoneId");
        if (controller != null && exileZoneId != null) {
            ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
            if (exileZone != null) {
                int numCards = exileZone.size();
                if (numCards > 0) {
                    controller.moveCards(exileZone, Zone.HAND, source, game);
                    controller.loseLife(numCards, game, source, false);
                    return true;
                }
            }
        }
        return false;
    }
}
