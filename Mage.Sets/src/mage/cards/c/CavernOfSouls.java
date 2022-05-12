package mage.cards.c;

import java.util.*;
import mage.ConditionalMana;
import mage.MageObject;
import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author noxx
 */
public final class CavernOfSouls extends CardImpl {

    public CavernOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // As Cavern of Souls enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered.
        Ability ability = new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new CavernOfSoulsManaBuilder(), true);
        this.addAbility(ability, new CavernOfSoulsWatcher(ability.getOriginalId()));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CavernOfSoulsCantCounterEffect()));
    }

    private CavernOfSouls(final CavernOfSouls card) {
        super(card);
    }

    @Override
    public CavernOfSouls copy() {
        return new CavernOfSouls(this);
    }
}

class CavernOfSoulsManaBuilder extends ConditionalManaBuilder {

    SubType creatureType;

    @Override
    public ConditionalManaBuilder setMana(Mana mana, Ability source, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (subType != null) {
            creatureType = subType;
        }
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null && mana.getAny() == 0) {
            game.informPlayers(controller.getLogName() + " produces " + mana.toString() + " with " + sourceObject.getLogName()
                    + " (can only be spend to cast for creatures of type " + creatureType + " and that spell can't be countered)");
        }
        return super.setMana(mana, source, game);
    }

    @Override
    public ConditionalMana build(Object... options) {
        return new CavernOfSoulsConditionalMana(this.mana, creatureType);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered";
    }
}

class CavernOfSoulsConditionalMana extends ConditionalMana {

    public CavernOfSoulsConditionalMana(Mana mana, SubType creatureType) {
        super(mana);
        staticText = "Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered";
        addCondition(new CavernOfSoulsManaCondition(creatureType));
    }
}

class CavernOfSoulsManaCondition extends CreatureCastManaCondition {

    SubType creatureType;

    CavernOfSoulsManaCondition(SubType creatureType) {
        this.creatureType = creatureType;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        // check: ... to cast a creature spell
        if (super.apply(game, source)) {
            // check: ... of the chosen type
            MageObject object = game.getObject(source);
            if (creatureType != null && object != null && object.hasSubtype(creatureType, game)) {
                return true;
            }
        }
        return false;
    }
}

class CavernOfSoulsWatcher extends Watcher {

    private final Set<MageObjectReference> spells = new HashSet<>();
    private final UUID originalId;

    public CavernOfSoulsWatcher(UUID originalId) {
        super(WatcherScope.CARD);
        this.originalId = originalId;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            if (event.getData() != null && event.getData().equals(originalId.toString()) && event.getTargetId() != null) {
                spells.add(new MageObjectReference(game.getObject(event.getTargetId()), game));
            }
        }
    }

    public boolean spellCantBeCountered(MageObjectReference mor) {
        return spells.contains(mor);
    }

    @Override
    public void reset() {
        super.reset();
        spells.clear();
    }
}

class CavernOfSoulsCantCounterEffect extends ContinuousRuleModifyingEffectImpl {

    public CavernOfSoulsCantCounterEffect() {
        super(Duration.EndOfGame, Outcome.Benefit);
        staticText = null;
    }

    public CavernOfSoulsCantCounterEffect(final CavernOfSoulsCantCounterEffect effect) {
        super(effect);
    }

    @Override
    public CavernOfSoulsCantCounterEffect copy() {
        return new CavernOfSoulsCantCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null) {
            return "This spell can't be countered because a colored mana from " + sourceObject.getName() + " was spent to cast it.";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        CavernOfSoulsWatcher watcher = game.getState().getWatcher(CavernOfSoulsWatcher.class, source.getSourceId());
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && watcher != null && watcher.spellCantBeCountered(new MageObjectReference(spell, game));
    }
}
