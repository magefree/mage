package mage.cards.v;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardWithHalves;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ValgavothTerrorEater extends CardImpl {

    public ValgavothTerrorEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Ward--Sacrifice three nonland permanents.
        this.addAbility(new WardAbility(new SacrificeTargetCost(3, StaticFilters.FILTER_PERMANENTS_NON_LAND), false));

        // If a card you didn't control would be put into an opponent's graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(new ValgavothTerrorEaterReplacementEffect()));

        // During your turn, you may play cards exiled with Valgavoth. If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost.
        this.addAbility(new SimpleStaticAbility(new ValgavothTerrorEaterPlayEffect()).setIdentifier(MageIdentifier.ValgavothTerrorEaterAlternateCast));
    }

    private ValgavothTerrorEater(final ValgavothTerrorEater card) {
        super(card);
    }

    @Override
    public ValgavothTerrorEater copy() {
        return new ValgavothTerrorEater(this);
    }
}

class ValgavothTerrorEaterReplacementEffect extends GraveyardFromAnywhereExileReplacementEffect {

    ValgavothTerrorEaterReplacementEffect() {
        super(StaticFilters.FILTER_CARD_A, false);
        staticText = "If a card you didn't control would be put into an opponent's graveyard from anywhere, exile it instead";
    }

    private ValgavothTerrorEaterReplacementEffect(final ValgavothTerrorEaterReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ValgavothTerrorEaterReplacementEffect copy() {
        return new ValgavothTerrorEaterReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // TODO: part of #13594 refactor to replaceEvent (add exile zone info in ZoneChangeEvent?)
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            return controller.moveCardsToExile(
                    permanent, source, game, true,
                    CardUtil.getCardExileZoneId(game, source),
                    CardUtil.createObjectRelatedWindowTitle(source, game, "(may be played using life)")
            );
        }

        Card card = game.getCard(event.getTargetId());
        if (card != null) {
            return controller.moveCardsToExile(
                    card, source, game, true,
                    CardUtil.getCardExileZoneId(game, source),
                    CardUtil.createObjectRelatedWindowTitle(source, game, "(may be played using life)"));
        }

        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // checks that zce movement is to graveyard
        if (!super.applies(event, source, game)) {
            return false;
        }

        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }

        Card card = game.getCard(event.getTargetId());
        if (card == null) {
            return false;
        }

        if (zce.getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = zce.getTarget();
            // is the permanent being moved controlled by someone that's not you?
            if (permanent == null || permanent.getControllerId().equals(controllerId)) {
                return false;
            }
        } else if (zce.getFromZone() == Zone.STACK) {
            Spell spell = game.getSpellOrLKIStack(event.getTargetId());
            if (spell == null) {
                // there is no direct link between moving a split/mdfc card and the stack part that was cast.
                // so we try them both and see if we find anything.
                if (card instanceof CardWithHalves) {
                    CardWithHalves cwh = (CardWithHalves) card;
                    spell = game.getSpellOrLKIStack(cwh.getLeftHalfCard().getId());
                    if (spell == null) {
                        spell = game.getSpellOrLKIStack(cwh.getRightHalfCard().getId());
                    }
                }
            }
            // is the spell being moved controlled by someone that's not you?
            if (spell == null || spell.getControllerId().equals(controllerId)) {
                return false;
            }
        }

        // is the card going to an opponent's graveyard?
        return controller.hasOpponent(card.getOwnerId(), game);
    }
}

class ValgavothTerrorEaterPlayEffect extends AsThoughEffectImpl {

    ValgavothTerrorEaterPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.AIDontUseIt); // AI will need help with this
        staticText = "during your turn, you may play cards exiled with {this}. If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost";
    }

    private ValgavothTerrorEaterPlayEffect(final ValgavothTerrorEaterPlayEffect effect) {
        super(effect);
    }

    @Override
    public ValgavothTerrorEaterPlayEffect copy() {
        return new ValgavothTerrorEaterPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId) || !game.isActivePlayer(affectedControllerId)) {
            return false;
        }
        Player player = game.getPlayer(affectedControllerId);
        if (player == null) {
            return false;
        }

        Card card = game.getCard(objectId);
        if (card == null) {
            return false;
        }
        UUID mainId = card.getMainCard().getId(); // for split cards

        MageObject sourceObject = source.getSourceObject(game);
        UUID exileZoneId = CardUtil.getExileZoneId(game, sourceObject.getId(), sourceObject.getZoneChangeCounter(game));
        ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
        if (exileZone == null || !exileZone.contains(mainId)) {
            return false;
        }

        // allows to play/cast with alternative life cost
        if (!card.isLand(game)) {
            PayLifeCost lifeCost = new PayLifeCost(card.getSpellAbility().getManaCosts().manaValue());
            Costs newCosts = new CostsImpl();
            newCosts.add(lifeCost);
            newCosts.addAll(card.getSpellAbility().getCosts());
            player.setCastSourceIdWithAlternateMana(card.getId(), null, newCosts, MageIdentifier.ValgavothTerrorEaterAlternateCast);
        }
        return true;
    }
}
