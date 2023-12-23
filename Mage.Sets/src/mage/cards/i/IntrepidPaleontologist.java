package mage.cards.i;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class IntrepidPaleontologist extends CardImpl {

    public IntrepidPaleontologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {2}: Exile target card from a graveyard.
        Ability exileAbility = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new GenericManaCost(2));
        exileAbility.addTarget(new TargetCardInGraveyard());
        this.addAbility(exileAbility);

        // You may cast Dinosaur creature spells from among cards you own exiled with Intrepid Paleontologist. If you cast a spell this way, that creature enters the battlefield with a finality counter on it.
        Ability castAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new IntrepidPaleontologistPlayEffect());
        castAbility.setIdentifier(MageIdentifier.IntrepidPaleontologistWatcher);
        castAbility.addWatcher(new IntrepidPaleontologistWatcher());
        this.addAbility(castAbility);
    }

    private IntrepidPaleontologist(final IntrepidPaleontologist card) {
        super(card);
    }

    @Override
    public IntrepidPaleontologist copy() {
        return new IntrepidPaleontologist(this);
    }
}

class IntrepidPaleontologistPlayEffect extends AsThoughEffectImpl {

    public IntrepidPaleontologistPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast Dinosaur creature spells from among cards you own exiled with {this}. If you cast a spell this way, that creature enters the battlefield with a finality counter on it.";
    }

    private IntrepidPaleontologistPlayEffect(final IntrepidPaleontologistPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IntrepidPaleontologistPlayEffect copy() {
        return new IntrepidPaleontologistPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (playerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            MageObject sourceObject = game.getObject(source);
            if (card != null && sourceObject != null && affectedAbility instanceof SpellAbility) {
                Card characteristics = ((SpellAbility)affectedAbility).getCharacteristics(game);
                if (card.getOwnerId().equals(playerId) && characteristics.isCreature(game) && characteristics.hasSubtype(SubType.DINOSAUR, game)) {
                    UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), sourceObject.getZoneChangeCounter(game));
                    ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
                    return exileZone != null && exileZone.contains(objectId);
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }
}

class IntrepidPaleontologistWatcher extends Watcher {
    IntrepidPaleontologistWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.IntrepidPaleontologistWatcher)) {
            Spell target = game.getSpell(event.getTargetId());
            if (target != null) {
                game.getState().addEffect(new AddCounterEnteringCreatureEffect(new MageObjectReference(target.getCard(), game),
                                CounterType.FINALITY.createInstance(), Outcome.UnboostCreature),
                        target.getSpellAbility());
            }
        }
    }
}
