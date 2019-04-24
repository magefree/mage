package mage.game.stack;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.*;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.text.TextPart;
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.GameLog;
import mage.util.SubTypeList;
import mage.watchers.Watcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class StackAbility extends StackObjImpl implements Ability {

    private static EnumSet<CardType> emptyCardType = EnumSet.noneOf(CardType.class);
    private static List<String> emptyString = new ArrayList<>();
    private static ObjectColor emptyColor = new ObjectColor();
    private static ManaCosts<ManaCost> emptyCost = new ManaCostsImpl<>();
    private static Costs<Cost> emptyCosts = new CostsImpl<>();
    private static Abilities<Ability> emptyAbilites = new AbilitiesImpl<>();

    private final Ability ability;
    private UUID controllerId;
    private String name;
    private String expansionSetCode;
    private TargetAdjuster targetAdjuster = null;

    public StackAbility(Ability ability, UUID controllerId) {
        this.ability = ability;
        this.controllerId = controllerId;
        this.name = "stack ability (" + ability.getRule() + ')';
    }

    public StackAbility(final StackAbility stackAbility) {
        this.ability = stackAbility.ability.copy();
        this.controllerId = stackAbility.controllerId;
        this.name = stackAbility.name;
        this.expansionSetCode = stackAbility.expansionSetCode;
        this.targetAdjuster = stackAbility.targetAdjuster;
        this.targetChanged = stackAbility.targetChanged;
    }

    @Override
    public boolean isActivated() {
        return ability.isActivated();
    }

    @Override
    public boolean resolve(Game game) {
        if (ability.getTargets().stillLegal(ability, game) || !canFizzle()) {
            boolean result = ability.resolve(game);
            game.getStack().remove(this, game);
            return result;
        }
        if (!game.isSimulation()) {
            game.informPlayers("Ability has been fizzled: " + getRule());
        }
        counter(null, game);
        game.getStack().remove(this, game);
        return false;
    }

    @Override
    public void reset(Game game) {
    }

    @Override
    public void counter(UUID sourceId, Game game) {
        // zone, owner, top ignored
        this.counter(sourceId, game, Zone.GRAVEYARD, true, ZoneDetail.TOP);
    }

    @Override
    public void counter(UUID sourceId, Game game, Zone zone, boolean owner, ZoneDetail zoneDetail) {
        //20100716 - 603.8
        if (ability instanceof StateTriggeredAbility) {
            ((StateTriggeredAbility) ability).counter(game);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIdName() {
        return getName() + " [" + getId().toString().substring(0, 3) + ']';
    }

    @Override
    public String getLogName() {
        return GameLog.getColoredObjectIdName(this);
    }

    @Override
    public String getImageName() {
        return name;
    }

    public String getExpansionSetCode() {
        return expansionSetCode;
    }

    @Override
    public EnumSet<CardType> getCardType() {
        return emptyCardType;
    }

    @Override
    public SubTypeList getSubtype(Game game) {
        return new SubTypeList();
    }

    @Override
    public boolean hasSubtype(SubType subtype, Game game) {
        return false;
    }

    @Override
    public EnumSet<SuperType> getSuperType() {
        return EnumSet.noneOf(SuperType.class);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        Abilities<Ability> abilities = new AbilitiesImpl<>();
        abilities.add(ability);
        return abilities;
    }

    @Override
    public boolean hasAbility(UUID abilityId, Game game) {
        return false;
    }

    @Override
    public ObjectColor getColor(Game game) {
        return emptyColor;
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return ability.getSourceObject(game).getFrameColor(game);
    }

    @Override
    public FrameStyle getFrameStyle() {
        // Abilities all use the same frame
        return FrameStyle.M15_NORMAL;
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return emptyCost;
    }

    @Override
    public MageInt getPower() {
        return MageInt.EmptyMageInt;
    }

    @Override
    public MageInt getToughness() {
        return MageInt.EmptyMageInt;
    }

    @Override
    public int getStartingLoyalty() {
        return 0;
    }

    @Override
    public Zone getZone() {
        return this.ability.getZone();
    }

    @Override
    public UUID getId() {
        return this.ability.getId();
    }

    @Override
    public UUID getSourceId() {
        return this.ability.getSourceId();
    }

    @Override
    public UUID getControllerId() {
        return this.controllerId;
    }

    @Override
    public Costs<Cost> getCosts() {
        return emptyCosts;
    }

    @Override
    public int getConvertedManaCost() {
        // Activated abilities have an "activation cost" but they don't have a characteristic related to that while on the stack.
        // There are certain effects that interact with the cost to activate an ability (e.g., Training Grounds, Power Artifact)
        // but nothing that looks for that quality of an ability once it's on the stack.
        return 0;
    }

    @Override
    public Effects getEffects() {
        return ability.getEffects();
    }

    @Override
    public Effects getAllEffects() {
        return ability.getAllEffects();
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        return ability.getEffects(game, effectType);
    }

    @Override
    public String getRule() {
        return ability.getRule();
    }

    @Override
    public String getRule(boolean all) {
        return ability.getRule(all);
    }

    @Override
    public String getRule(String source) {
        return ability.getRule(source);
    }

    @Override
    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
    }

    @Override
    public void setSourceId(UUID sourceID) {
    }

    @Override
    public void addCost(Cost cost) {
    }

    @Override
    public void addEffect(Effect effect) {
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        return ability.activate(game, noMana);
    }

    @Override
    public Targets getTargets() {
        return ability.getTargets();
    }

    @Override
    public void addTarget(Target target) {
    }

    @Override
    public UUID getFirstTarget() {
        return ability.getFirstTarget();
    }

    @Override
    public ManaCosts<ManaCost> getManaCosts() {
        return ability.getManaCosts();
    }

    @Override
    public ManaCosts<ManaCost> getManaCostsToPay() {
        return ability.getManaCostsToPay();
    }

    @Override
    public void addManaCost(ManaCost cost) {
    }

    @Override
    public AbilityType getAbilityType() {
        return ability.getAbilityType();
    }

    @Override
    public boolean isUsesStack() {
        return true;
    }

    @Override
    public StackAbility copy() {
        return new StackAbility(this);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setExpansionSetCode(String expansionSetCode) {
        this.expansionSetCode = expansionSetCode;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Card card = game.getCard(ability.getSourceId());
        if (card != null) {
            card.adjustCosts(ability, game);
        }
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Card card = game.getCard(ability.getSourceId());
        if (card != null) {
            card.adjustTargets(ability, game);
        }
    }

    @Override
    public Costs<Cost> getOptionalCosts() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addOptionalCost(Cost cost) {
    }

    @Override
    public boolean checkIfClause(Game game) {
        return true;
    }

    @Override
    public void newId() {
        if (!(this instanceof MageSingleton)) {
            this.ability.newId();
        }
    }

    @Override
    public void newOriginalId() {
    }

    @Override
    public Ability getStackAbility() {
        return ability;
    }

    @Override
    public boolean isModal() {
        return ability.isModal();
    }

    @Override
    public void addMode(Mode mode) {
    }

    @Override
    public Modes getModes() {
        return ability.getModes();
    }

    @Override
    public boolean canChooseTarget(Game game) {
        return ability.canChooseTarget(game);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean hasSourceObjectAbility(Game game, MageObject source, GameEvent event) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void setCopy(boolean isCopy) {
    }

    @Override
    public boolean isCopy() {
        return false;
    }

    @Override
    public boolean getRuleAtTheTop() {
        return this.ability.getRuleAtTheTop();
    }

    @Override
    public Ability setRuleAtTheTop(boolean ruleAtTheTop) {
        this.ability.setRuleAtTheTop(ruleAtTheTop);
        return this;
    }

    @Override
    public boolean getRuleVisible() {
        return this.ability.getRuleVisible();
    }

    @Override
    public void setRuleVisible(boolean ruleVisible) {
        this.ability.setRuleVisible(ruleVisible);
    }

    @Override
    public boolean getAdditionalCostsRuleVisible() {
        return this.ability.getAdditionalCostsRuleVisible();
    }

    @Override
    public void setAdditionalCostsRuleVisible(boolean ruleAdditionalCostsVisible) {
        this.ability.setAdditionalCostsRuleVisible(ruleAdditionalCostsVisible);
    }

    @Override
    public UUID getOriginalId() {
        return this.ability.getOriginalId();
    }

    @Override
    public void setAbilityWord(AbilityWord abilityWord) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean activateAlternateOrAdditionalCosts(MageObject sourceObject, boolean noMana, Player controller, Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getGameLogMessage(Game game) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCostModificationActive(boolean active) {
        throw new UnsupportedOperationException("Not supported. Only neede for flashbacked spells");
    }

    @Override
    public boolean getWorksFaceDown() {
        return this.ability.getWorksFaceDown();
    }

    @Override
    public void setWorksFaceDown(boolean worksFaceDown) {
        this.ability.setWorksFaceDown(worksFaceDown);
    }

    @Override
    public List<Watcher> getWatchers() {
        return this.ability.getWatchers();
    }

    @Override
    public void addWatcher(Watcher watcher) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public List<Ability> getSubAbilities() {
        return this.ability.getSubAbilities();
    }

    @Override
    public void addSubAbility(Ability ability) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public MageObject getSourceObject(Game game) {
        return this.ability.getSourceObject(game);
    }

    @Override
    public MageObject getSourceObjectIfItStillExists(Game game) {
        return this.ability.getSourceObjectIfItStillExists(game);
    }

    @Override
    public Permanent getSourcePermanentIfItStillExists(Game game) {
        return this.ability.getSourcePermanentIfItStillExists(game);
    }

    @Override
    public int getSourceObjectZoneChangeCounter() {
        return ability.getSourceObjectZoneChangeCounter();
    }

    @Override
    public void setSourceObject(MageObject sourceObject, Game game) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return game.getState().getZoneChangeCounter(getSourceId());
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public String getTargetDescription(Targets targets, Game game) {
        return getAbilities().get(0).getTargetDescription(targets, game);
    }

    @Override
    public boolean canFizzle() {
        return ability.canFizzle();
    }

    @Override
    public void setCanFizzle(boolean canFizzle) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public StackObject createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets) {
        Ability newAbility = this.copy();
        newAbility.newId();
        StackAbility newStackAbility = new StackAbility(newAbility, newControllerId);
        game.getStack().push(newStackAbility);
        if (chooseNewTargets && !newAbility.getTargets().isEmpty()) {
            Player controller = game.getPlayer(newControllerId);
            Outcome outcome = newAbility.getEffects().isEmpty() ? Outcome.Detriment : newAbility.getEffects().get(0).getOutcome();
            if (controller.chooseUse(outcome, "Choose new targets?", source, game)) {
                newAbility.getTargets().clearChosen();
                newAbility.getTargets().chooseTargets(outcome, newControllerId, newAbility, false, game);
            }
        }
        game.fireEvent(new GameEvent(GameEvent.EventType.COPIED_STACKOBJECT, newStackAbility.getId(), this.getId(), newControllerId));
        return newStackAbility;
    }

    @Override
    public boolean isAllCreatureTypes() {
        return false;
    }

    @Override
    public void setIsAllCreatureTypes(boolean value) {
    }

    @Override
    public List<TextPart> getTextParts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TextPart addTextPart(TextPart textPart) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTargetAdjuster(TargetAdjuster targetAdjuster) {
        this.targetAdjuster = targetAdjuster;
    }

    @Override
    public TargetAdjuster getTargetAdjuster() {
        return targetAdjuster;
    }

    @Override
    public void adjustTargets(Game game) {
        if (targetAdjuster != null) {
            targetAdjuster.adjustTargets(this, game);
        }
    }
}
