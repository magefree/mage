package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GrimoireThief extends CardImpl {

    protected static final String VALUE_PREFIX = "ExileZones";

    public GrimoireThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Grimoire Thief becomes tapped, exile the top three 
        // cards of target opponent's library face down.
        Ability ability = new BecomesTappedSourceTriggeredAbility(
                new GrimoireThiefExileEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // You may look at cards exiled with Grimoire Thief.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GrimoireThiefLookEffect()));

        // {U}, Sacrifice Grimoire Thief: Turn all cards exiled with 
        //Grimoire Thief face up. Counter all spells with those names.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GrimoireThiefCounterspellEffect(), new ManaCostsImpl("{U}"));
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);

    }

    private GrimoireThief(final GrimoireThief card) {
        super(card);
    }

    @Override
    public GrimoireThief copy() {
        return new GrimoireThief(this);
    }
}

class GrimoireThiefExileEffect extends OneShotEffect {

    public GrimoireThiefExileEffect() {
        super(Outcome.Discard);
        staticText = "exile the top three cards of target opponent's library face down";
    }

    public GrimoireThiefExileEffect(final GrimoireThiefExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if (targetOpponent != null) {
            Set<Card> cards = targetOpponent.getLibrary().getTopCards(game, 3);
            MageObject sourceObject = source.getSourceObject(game);
            if (!cards.isEmpty() && sourceObject != null) {
                for (Card card : cards) {
                    card.setFaceDown(true, game);
                }
                UUID exileZoneId = CardUtil.getExileZoneId(game,
                        source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                targetOpponent.moveCardsToExile(cards, source, game, false,
                        exileZoneId, sourceObject.getIdName());
                for (Card card : cards) {
                    card.setFaceDown(true, game);
                }
                Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(
                        GrimoireThief.VALUE_PREFIX + source.getSourceId().toString());
                if (exileZones == null) {
                    exileZones = new HashSet<>();
                    game.getState().setValue(GrimoireThief.VALUE_PREFIX
                            + source.getSourceId().toString(), exileZones);
                }
                exileZones.add(exileZoneId);
                return true;
            }
        }
        return false;
    }

    @Override
    public GrimoireThiefExileEffect copy() {
        return new GrimoireThiefExileEffect(this);
    }
}

class GrimoireThiefLookEffect extends AsThoughEffectImpl {

    public GrimoireThiefLookEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    public GrimoireThiefLookEffect(final GrimoireThiefLookEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GrimoireThiefLookEffect copy() {
        return new GrimoireThiefLookEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())
                && game.getState().getZone(objectId) == Zone.EXILED) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = source.getSourceObject(game);
            if (controller != null
                    && sourceObject != null) {
                Card card = game.getCard(objectId);
                if (card != null
                        && card.isFaceDown(game)) {
                    Set<UUID> exileZones = (Set<UUID>) game.getState().
                            getValue(GrimoireThief.VALUE_PREFIX + source.getSourceId().toString());
                    if (exileZones != null) {
                        for (ExileZone exileZone : game.getExile().getExileZones()) {
                            if (exileZone.contains(objectId)) {
                                if (!exileZones.contains(exileZone.getId())) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class GrimoireThiefCounterspellEffect extends OneShotEffect {

    public GrimoireThiefCounterspellEffect() {
        super(Outcome.Discard);
        staticText = "Turn all cards exiled with {this} face up. "
                + "Counter all spells with those names";
    }

    public GrimoireThiefCounterspellEffect(final GrimoireThiefCounterspellEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = new CardsImpl();
        MageObject sourceObject = game.getObject(source.getSourceId());
        Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(
                GrimoireThief.VALUE_PREFIX + source.getSourceId().toString());
        if (exileZones != null && sourceObject != null) {
            for (ExileZone exileZone : game.getExile().getExileZones()) {
                if (!exileZone.isEmpty()) {
                    cards.addAll(exileZone.getCards(game));
                }
            }
            // set face up first
            for (Card card : cards.getCards(game)) {
                card.setFaceDown(false, game);
            }
            // then counter any with the same name as the card exiled with Grimoire Thief
            for (Card card : cards.getCards(game)) {
                for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext(); ) {
                    StackObject stackObject = iterator.next();
                    MageObject mageObject = game.getObject(card.getId());
                    String name1;
                    String name2;
                    if (mageObject instanceof SplitCard) {
                        name1 = ((SplitCard) mageObject).getLeftHalfCard().getName();
                        name2 = ((SplitCard) mageObject).getRightHalfCard().getName();
                    } else {
                        // modal double faces cards, adventure cards -- all have one name in non stack/battlefield zone
                        name1 = mageObject.getName();
                        name2 = name1;
                    }

                    if (CardUtil.haveSameNames(stackObject, name1, game) || CardUtil.haveSameNames(stackObject, name2, game)) {
                        Spell spell = (Spell) stackObject;
                        game.getStack().counter(stackObject.getId(), source, game);
                        game.informPlayers(sourceObject.getLogName() + ": spell " + spell.getIdName() + " was countered.");
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public GrimoireThiefCounterspellEffect copy() {
        return new GrimoireThiefCounterspellEffect(this);
    }
}
