package mage.game.stack;

import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.text.TextPart;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.cards.FrameStyle;
import mage.cards.SplitCard;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.GameState;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.util.GameLog;
import mage.util.SubTypeList;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Spell extends StackObjImpl implements Card {

    private final List<SpellAbility> spellAbilities = new ArrayList<>();
    private final List<Card> spellCards = new ArrayList<>();

    private static final String regexBlack = ".*\\x7b.{0,2}B.{0,2}\\x7d.*";
    private static final String regexBlue = ".*\\x7b.{0,2}U.{0,2}\\x7d.*";
    private static final String regexRed = ".*\\x7b.{0,2}R.{0,2}\\x7d.*";
    private static final String regexGreen = ".*\\x7b.{0,2}G.{0,2}\\x7d.*";
    private static final String regexWhite = ".*\\x7b.{0,2}W.{0,2}\\x7d.*";

    private final Card card;
    private final ObjectColor color;
    private final ObjectColor frameColor;
    private final FrameStyle frameStyle;
    private final SpellAbility ability;
    private final Zone fromZone;
    private final UUID id;

    private UUID controllerId;
    private boolean copy;
    private MageObject copyFrom; // copied card INFO (used to call original adjusters)
    private boolean faceDown;
    private boolean countered;
    private boolean resolving = false;
    private UUID commandedBy = null; // for Word of Command

    private boolean doneActivatingManaAbilities; // if this is true, the player is no longer allowed to pay the spell costs with activating of mana abilies

    public Spell(Card card, SpellAbility ability, UUID controllerId, Zone fromZone) {
        this.card = card;
        this.color = card.getColor(null).copy();
        this.frameColor = card.getFrameColor(null).copy();
        this.frameStyle = card.getFrameStyle();
        id = ability.getId();
        this.ability = ability;
        this.ability.setControllerId(controllerId);
        if (ability.getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            spellCards.add(((SplitCard) card).getLeftHalfCard());
            spellAbilities.add(((SplitCard) card).getLeftHalfCard().getSpellAbility().copy());
            spellCards.add(((SplitCard) card).getRightHalfCard());
            spellAbilities.add(((SplitCard) card).getRightHalfCard().getSpellAbility().copy());
        } else {
            spellCards.add(card);
            spellAbilities.add(ability);
        }
        this.controllerId = controllerId;
        this.fromZone = fromZone;
        this.countered = false;
    }

    public Spell(final Spell spell) {
        this.id = spell.id;
        for (SpellAbility spellAbility : spell.spellAbilities) {
            this.spellAbilities.add(spellAbility.copy());
        }
        for (Card spellCard : spell.spellCards) {
            this.spellCards.add(spellCard.copy());
        }
        if (spell.spellAbilities.get(0).equals(spell.ability)) {
            this.ability = this.spellAbilities.get(0);
        } else {
            this.ability = spell.ability.copy();
        }
        if (spell.spellCards.get(0).equals(spell.card)) {
            this.card = spellCards.get(0);
        } else {
            this.card = spell.card.copy();
        }

        this.fromZone = spell.fromZone;
        this.color = spell.color.copy();
        this.frameColor = spell.color.copy();
        this.frameStyle = spell.frameStyle;

        this.controllerId = spell.controllerId;
        this.copy = spell.copy;
        this.copyFrom = (spell.copyFrom != null ? spell.copyFrom.copy() : null);
        this.faceDown = spell.faceDown;
        this.countered = spell.countered;
        this.resolving = spell.resolving;
        this.commandedBy = spell.commandedBy;

        this.doneActivatingManaAbilities = spell.doneActivatingManaAbilities;
        this.targetChanged = spell.targetChanged;
    }

    public boolean activate(Game game, boolean noMana) {
        setDoneActivatingManaAbilities(false); // Used for e.g. improvise
        if (!spellAbilities.get(0).activate(game, noMana)) {
            return false;
        }
        if (spellAbilities.size() > 1) {
            // if there are more abilities (fused split spell) or first ability added new abilities (splice), activate the additional abilities
            boolean ignoreAbility = true;
            boolean payNoMana = noMana;
            for (SpellAbility spellAbility : spellAbilities) {
                if (ignoreAbility) {
                    ignoreAbility = false;
                } else {
                    // costs for spliced abilities were added to main spellAbility, so pay no mana for spliced abilities
                    payNoMana |= spellAbility.getSpellAbilityType() == SpellAbilityType.SPLICE;
                    if (!spellAbility.activate(game, payNoMana)) {
                        return false;
                    }
                }
            }
        }
        setDoneActivatingManaAbilities(true); // can be activated again maybe during the resolution of the spell (e.g. Metallic Rebuke)
        return true;
    }

    public String getActivatedMessage(Game game) {
        StringBuilder sb = new StringBuilder();
        if (isCopy()) {
            sb.append(" copies ");
        } else {
            sb.append(" casts ");
        }
        return sb.append(ability.getGameLogMessage(game)).toString();
    }

    public String getSpellCastText(Game game) {
        for (Ability spellAbility : getAbilities()) {
            if (spellAbility instanceof MorphAbility
                    && ((AlternativeSourceCosts) spellAbility).isActivated(getSpellAbility(), game)) {
                return "a card face down";
            }
        }
        return GameLog.replaceNameByColoredName(card, getSpellAbility().toString());
    }

    @Override
    public boolean resolve(Game game) {
        boolean result;
        Player controller = game.getPlayer(getControllerId());
        if (controller == null) {
            return false;
        }
        this.resolving = true;
        if (commandedBy != null && !commandedBy.equals(getControllerId())) {
            Player turnController = game.getPlayer(commandedBy);
            if (turnController != null) {
                turnController.controlPlayersTurn(game, controller.getId());
            }
        }
        if (this.isInstant() || this.isSorcery()) {
            int index = 0;
            result = false;
            boolean legalParts = false;
            boolean notTargeted = true;
            // check for legal parts
            for (SpellAbility spellAbility : this.spellAbilities) {
                // if muliple modes are selected, and there are modes with targets, then at least one mode has to have a legal target or
                // When resolving a fused split spell with multiple targets, treat it as you would any spell with multiple targets.
                // If all targets are illegal when the spell tries to resolve, the spell is countered and none of its effects happen.
                // If at least one target is still legal at that time, the spell resolves, but an illegal target can't perform any actions
                // or have any actions performed on it.
                // if only a spliced spell has targets and all targets ar illegal, the complete spell is countered
                if (hasTargets(spellAbility, game)) {
                    notTargeted = false;
                    legalParts |= spellAbilityHasLegalParts(spellAbility, game);
                }

            }
            // resolve if legal parts
            if (notTargeted || legalParts) {
                for (SpellAbility spellAbility : this.spellAbilities) {
                    if (spellAbilityHasLegalParts(spellAbility, game)) {
                        for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
                            spellAbility.getModes().setActiveMode(modeId);
                            if (spellAbility.getTargets().stillLegal(spellAbility, game)) {
                                if (spellAbility.getSpellAbilityType() != SpellAbilityType.SPLICE) {
                                    updateOptionalCosts(index);
                                }
                                result |= spellAbility.resolve(game);
                            }
                        }
                        index++;
                    }
                }
                if (game.getState().getZone(card.getMainCard().getId()) == Zone.STACK) {
                    if (!isCopy()) {
                        controller.moveCards(card, Zone.GRAVEYARD, ability, game);
                    }
                }
                return result;
            }
            //20091005 - 608.2b
            if (!game.isSimulation()) {
                game.informPlayers(getName() + " has been fizzled.");
            }
            counter(null, game);
            return false;
        } else if (this.isEnchantment() && this.hasSubtype(SubType.AURA, game)) {
            if (ability.getTargets().stillLegal(ability, game)) {
                updateOptionalCosts(0);
                boolean bestow = ability instanceof BestowAbility;
                if (bestow) {
                    // Must be removed first time, after that will be removed by continous effect
                    // Otherwise effects like evolve trigger from creature comes into play event
                    card.getCardType().remove(CardType.CREATURE);
                    if (!card.getSubtype(game).contains(SubType.AURA)) {
                        card.getSubtype(game).add(SubType.AURA);
                    }
                }
                if (controller.moveCards(card, Zone.BATTLEFIELD, ability, game, false, faceDown, false, null)) {
                    if (bestow) {
                        // card will be copied during putOntoBattlefield, so the card of CardPermanent has to be changed
                        // TODO: Find a better way to prevent bestow creatures from being effected by creature affecting abilities
                        Permanent permanent = game.getPermanent(card.getId());
                        if (permanent instanceof PermanentCard) {
                            permanent.setSpellAbility(ability); // otherwise spell ability without bestow will be set
                            if (!card.getCardType().contains(CardType.CREATURE)) {
                                card.addCardType(CardType.CREATURE);
                            }
                            card.getSubtype(game).remove(SubType.AURA);
                        }
                    }
                    return ability.resolve(game);
                }
                if (bestow) {
                    card.addCardType(CardType.CREATURE);
                }
                return false;
            }
            // Aura has no legal target and its a bestow enchantment -> Add it to battlefield as creature
            if (this.getSpellAbility() instanceof BestowAbility) {
                updateOptionalCosts(0);
                if (controller.moveCards(card, Zone.BATTLEFIELD, ability, game, false, faceDown, false, null)) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent instanceof PermanentCard) {
                        ((PermanentCard) permanent).getCard().addCardType(CardType.CREATURE);
                        ((PermanentCard) permanent).getCard().getSubtype(game).remove(SubType.AURA);
                        return true;
                    }
                }
                return false;
            } else {
                //20091005 - 608.2b
                if (!game.isSimulation()) {
                    game.informPlayers(getName() + " has been fizzled.");
                }
                counter(null, game);
                return false;
            }
        } else {
            updateOptionalCosts(0);
            return controller.moveCards(card, Zone.BATTLEFIELD, ability, game, false, faceDown, false, null);
        }
    }

    private boolean hasTargets(SpellAbility spellAbility, Game game) {
        if (spellAbility.getModes().getSelectedModes().size() > 1) {
            for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
                Mode mode = spellAbility.getModes().get(modeId);
                if (!mode.getTargets().isEmpty()) {
                    return true;
                }

            }
            return false;
        } else {
            return !spellAbility.getTargets().isEmpty();
        }
    }

    private boolean spellAbilityHasLegalParts(SpellAbility spellAbility, Game game) {
        if (spellAbility.getModes().getSelectedModes().size() > 1) {
            boolean targetedMode = false;
            boolean legalTargetedMode = false;
            for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
                Mode mode = spellAbility.getModes().get(modeId);
                if (!mode.getTargets().isEmpty()) {
                    targetedMode = true;
                    if (mode.getTargets().stillLegal(spellAbility, game)) {
                        legalTargetedMode = true;
                    }
                }
            }
            if (targetedMode) {
                return legalTargetedMode;
            }
            return true;
        } else {
            return spellAbility.getTargets().stillLegal(spellAbility, game);
        }
    }

    /**
     * As we have ability in the stack, we need to update optional costs in
     * original card. This information will be used later by effects, e.g. to
     * determine whether card was kicked or not. E.g. Desolation Angel
     */
    private void updateOptionalCosts(int index) {
        spellCards.get(index).getAbilities().get(spellAbilities.get(index).getId()).ifPresent(abilityOrig
                -> {
            for (Object object : spellAbilities.get(index).getOptionalCosts()) {
                Cost cost = (Cost) object;
                for (Cost costOrig : abilityOrig.getOptionalCosts()) {
                    if (cost.getId().equals(costOrig.getId())) {
                        if (cost.isPaid()) {
                            costOrig.setPaid();
                        } else {
                            costOrig.clearPaid();
                        }
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void counter(UUID sourceId, Game game) {
        this.counter(sourceId, game, Zone.GRAVEYARD, false, ZoneDetail.NONE);
    }

    @Override
    public void counter(UUID sourceId, Game game, Zone zone, boolean owner, ZoneDetail zoneDetail) {
        this.countered = true;
        if (!isCopy()) {
            Player player = game.getPlayer(game.getControllerId(sourceId));
            if (player == null) {
                player = game.getPlayer(getControllerId());
            }
            if (player != null) {
                Ability counteringAbility = null;
                MageObject counteringObject = game.getObject(sourceId);
                if (counteringObject instanceof StackObject) {
                    counteringAbility = ((StackObject) counteringObject).getStackAbility();
                }
                if (zone == Zone.LIBRARY) {
                    if (zoneDetail == ZoneDetail.CHOOSE) {
                        if (player.chooseUse(Outcome.Detriment, "Move countered spell to the top of the library? (otherwise it goes to the bottom)", counteringAbility, game)) {
                            zoneDetail = ZoneDetail.TOP;
                        } else {
                            zoneDetail = ZoneDetail.BOTTOM;
                        }
                    }
                    if (zoneDetail == ZoneDetail.TOP) {
                        player.putCardsOnTopOfLibrary(new CardsImpl(card), game, counteringAbility, false);
                    } else {
                        player.putCardsOnBottomOfLibrary(new CardsImpl(card), game, counteringAbility, false);
                    }
                } else {
                    player.moveCards(card, zone, counteringAbility, game, false, false, owner, null);
                }
            }
        } else {
            // Copied spell, only remove from stack
            game.getStack().remove(this, game);
        }
    }

    public boolean isDoneActivatingManaAbilities() {
        return doneActivatingManaAbilities;
    }

    public void setDoneActivatingManaAbilities(boolean doneActivatingManaAbilities) {
        this.doneActivatingManaAbilities = doneActivatingManaAbilities;
    }

    @Override
    public UUID getSourceId() {
        return card.getId();
    }

    @Override
    public UUID getControllerId() {
        return this.controllerId;
    }

    @Override
    public String getName() {
        return card.getName();
    }

    @Override
    public String getIdName() {
        String idName;
        if (card != null) {
            idName = card.getId().toString().substring(0, 3);
        } else {
            idName = getId().toString().substring(0, 3);
        }
        return getName() + " [" + idName + ']';
    }

    @Override
    public String getLogName() {
        if (faceDown) {
            if (this.isCreature()) {
                return "face down creature spell";
            } else {
                return "face down spell";
            }
        }
        return GameLog.getColoredObjectIdName(card);
    }

    @Override
    public String getImageName() {
        return card.getImageName();
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public Rarity getRarity() {
        return card.getRarity();
    }

    @Override
    public Set<CardType> getCardType() {
        if (faceDown) {
            EnumSet<CardType> cardTypes = EnumSet.noneOf(CardType.class);
            cardTypes.add(CardType.CREATURE);
            return cardTypes;
        }
        if (this.getSpellAbility() instanceof BestowAbility) {
            EnumSet<CardType> cardTypes = EnumSet.noneOf(CardType.class);
            cardTypes.addAll(card.getCardType());
            cardTypes.remove(CardType.CREATURE);
            return cardTypes;
        }
        return card.getCardType();
    }

    @Override
    public SubTypeList getSubtype(Game game) {
        if (this.getSpellAbility() instanceof BestowAbility) {
            SubTypeList subtypes = card.getSubtype(game);
            if (!subtypes.contains(SubType.AURA)) { // do it only once
                subtypes.add(SubType.AURA);
            }
            return subtypes;
        }
        return card.getSubtype(game);
    }

    @Override
    public boolean hasSubtype(SubType subtype, Game game) {
        if (this.getSpellAbility() instanceof BestowAbility) { // workaround for Bestow (don't like it)
            SubTypeList subtypes = card.getSubtype(game);
            if (!subtypes.contains(SubType.AURA)) { // do it only once
                subtypes.add(SubType.AURA);
            }
            if (subtypes.contains(subtype)) {
                return true;
            }
        }
        return card.hasSubtype(subtype, game);
    }

    @Override
    public Set<SuperType> getSuperType() {
        return card.getSuperType();
    }

    public List<SpellAbility> getSpellAbilities() {
        return spellAbilities;
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return card.getAbilities();
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        return card.getAbilities(game);
    }

    @Override
    public boolean hasAbility(UUID abilityId, Game game) {
        return card.hasAbility(abilityId, game);
    }

    @Override
    public ObjectColor getColor(Game game) {
        return color;
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return frameColor;
    }

    @Override
    public FrameStyle getFrameStyle() {
        return frameStyle;
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return card.getManaCost();
    }

    /**
     * 202.3b When calculating the converted mana cost of an object with an {X}
     * in its mana cost, X is treated as 0 while the object is not on the stack,
     * and X is treated as the number chosen for it while the object is on the
     * stack.
     *
     * @return
     */
    @Override
    public int getConvertedManaCost() {
        int cmc = 0;
        if (faceDown) {
            return 0;
        }
        for (SpellAbility spellAbility : spellAbilities) {
            cmc += spellAbility.getConvertedXManaCost(getCard());
        }
        cmc += getCard().getManaCost().convertedManaCost();
        return cmc;
    }

    @Override
    public MageInt getPower() {
        return card.getPower();
    }

    @Override
    public MageInt getToughness() {
        return card.getToughness();
    }

    @Override
    public int getStartingLoyalty() {
        return card.getStartingLoyalty();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getOwnerId() {
        return card.getOwnerId();
    }

    public void addSpellAbility(SpellAbility spellAbility) {
        spellAbilities.add(spellAbility);
    }

    public void addAbility(Ability ability) {
    }

    @Override
    public SpellAbility getSpellAbility() {
        return ability;
    }

    public void setControllerId(UUID controllerId) {
        this.ability.setControllerId(controllerId);
        for (SpellAbility spellAbility : spellAbilities) {
            spellAbility.setControllerId(controllerId);
        }
        this.controllerId = controllerId;
    }

    @Override
    public void setOwnerId(UUID controllerId) {
    }

    @Override
    public List<String> getRules() {
        return card.getRules();
    }

    @Override
    public List<String> getRules(Game game) {
        return card.getRules(game);
    }

    @Override
    public String getExpansionSetCode() {
        return card.getExpansionSetCode();
    }

    @Override
    public String getTokenSetCode() {
        return card.getTokenSetCode();
    }

    @Override
    public String getTokenDescriptor() {
        return card.getTokenDescriptor();
    }

    @Override
    public void setFaceDown(boolean value, Game game) {
        faceDown = value;
    }

    @Override
    public boolean turnFaceUp(Game game, UUID playerId) {
        setFaceDown(false, game);
        return true;
    }

    @Override
    public boolean turnFaceDown(Game game, UUID playerId) {
        setFaceDown(true, game);
        return true;
    }

    @Override
    public boolean isFaceDown(Game game) {
        return faceDown;
    }

    @Override
    public boolean isFlipCard() {
        return false;
    }

    @Override
    public String getFlipCardName() {
        return null;
    }

    @Override
    public boolean isSplitCard() {
        return false;
    }

    @Override
    public boolean isTransformable() {
        return false;
    }

    @Override
    public Card getSecondCardFace() {
        return null;
    }

    @Override
    public boolean isNightCard() {
        return false;
    }

    @Override
    public Spell copy() {
        return new Spell(this);
    }

    public Spell copySpell(UUID newController) {
        Spell copy = new Spell(this.card, this.ability.copySpell(), this.controllerId, this.fromZone);
        boolean firstDone = false;
        for (SpellAbility spellAbility : this.getSpellAbilities()) {
            if (!firstDone) {
                firstDone = true;
                continue;
            }
            SpellAbility newAbility = spellAbility.copy(); // e.g. spliced spell
            newAbility.newId();
            copy.addSpellAbility(newAbility);
        }
        copy.setCopy(true, this);
        copy.setControllerId(newController);
        return copy;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (card != null) {
            card.adjustCosts(ability, game);
        }
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (card != null) {
            card.adjustTargets(ability, game);
        }
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, UUID sourceId) {
        return card.removeFromZone(game, fromZone, sourceId);
    }

    @Override
    public boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag) {
        return moveToZone(zone, sourceId, game, flag, null);
    }

    @Override
    public boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag, List<UUID> appliedEffects) {
        // 706.10a If a copy of a spell is in a zone other than the stack, it ceases to exist.
        // If a copy of a card is in any zone other than the stack or the battlefield, it ceases to exist.
        // These are state-based actions. See rule 704.
        if (this.isCopy() && zone != Zone.STACK) {
            return true;
        }
        return card.moveToZone(zone, sourceId, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
        return moveToExile(exileId, name, sourceId, game, null);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, List<UUID> appliedEffects) {
        if (this.isCopy()) {
            game.getStack().remove(this, game);
            return true;
        }
        return this.card.moveToExile(exileId, name, sourceId, game, appliedEffects);
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean facedown) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean facedown, List<UUID> appliedEffects) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getCardNumber() {
        return card.getCardNumber();
    }

    @Override
    public boolean getUsesVariousArt() {
        return card.getUsesVariousArt();
    }

    @Override
    public List<Mana> getMana() {
        return card.getMana();
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public Ability getStackAbility() {
        return this.ability;
    }

    @Override
    public void assignNewId() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void setTransformable(boolean value) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return card.getZoneChangeCounter(game);
    }

    @Override
    public void addInfo(String key, String value, Game game) {
        // do nothing
    }

    public Zone getFromZone() {
        return this.fromZone;
    }

    @Override
    public void setCopy(boolean isCopy, MageObject copyFrom) {
        this.copy = isCopy;
        this.copyFrom = (copyFrom != null ? copyFrom.copy() : null);
    }

    @Override
    public boolean isCopy() {
        return this.copy;
    }

    @Override
    public MageObject getCopyFrom() {
        return this.copyFrom;
    }

    @Override
    public Counters getCounters(Game game) {
        return card.getCounters(game);
    }

    @Override
    public Counters getCounters(GameState state) {
        return card.getCounters(state);
    }

    @Override
    public boolean addCounters(Counter counter, Ability source, Game game) {
        return card.addCounters(counter, source, game);
    }

    @Override
    public boolean addCounters(Counter counter, Ability source, Game game, boolean isEffect) {
        return card.addCounters(counter, source, game, isEffect);
    }

    @Override
    public boolean addCounters(Counter counter, Ability source, Game game, List<UUID> appliedEffects) {
        return card.addCounters(counter, source, game, appliedEffects);
    }

    @Override
    public boolean addCounters(Counter counter, Ability source, Game game, List<UUID> appliedEffects, boolean isEffect) {
        return card.addCounters(counter, source, game, appliedEffects, isEffect);
    }

    @Override
    public void removeCounters(String name, int amount, Game game) {
        card.removeCounters(name, amount, game);
    }

    @Override
    public void removeCounters(Counter counter, Game game) {
        card.removeCounters(counter, game);
    }

    public Card getCard() {
        return card;
    }

    @Override
    public Card getMainCard() {
        return card.getMainCard();
    }

    @Override
    public FilterMana getColorIdentity() {
        FilterMana mana = new FilterMana();
        mana.setBlack(getManaCost().getText().matches(regexBlack));
        mana.setBlue(getManaCost().getText().matches(regexBlue));
        mana.setGreen(getManaCost().getText().matches(regexGreen));
        mana.setRed(getManaCost().getText().matches(regexRed));
        mana.setWhite(getManaCost().getText().matches(regexWhite));

        for (String rule : getRules()) {
            rule = rule.replaceAll("(?i)<i.*?</i>", ""); // Ignoring reminder text in italic
            if (!mana.isBlack() && (rule.matches(regexBlack) || this.color.isBlack())) {
                mana.setBlack(true);
            }
            if (!mana.isBlue() && (rule.matches(regexBlue) || this.color.isBlue())) {
                mana.setBlue(true);
            }
            if (!mana.isGreen() && (rule.matches(regexGreen) || this.color.isGreen())) {
                mana.setGreen(true);
            }
            if (!mana.isRed() && (rule.matches(regexRed) || this.color.isRed())) {
                mana.setRed(true);
            }
            if (!mana.isWhite() && (rule.matches(regexWhite) || this.color.isWhite())) {
                mana.setWhite(true);
            }
        }
        if (isTransformable()) {
            Card secondCard = getSecondCardFace();
            ObjectColor color = secondCard.getColor(null);
            mana.setBlack(mana.isBlack() || color.isBlack());
            mana.setGreen(mana.isGreen() || color.isGreen());
            mana.setRed(mana.isRed() || color.isRed());
            mana.setBlue(mana.isBlue() || color.isBlue());
            mana.setWhite(mana.isWhite() || color.isWhite());
            for (String rule : secondCard.getRules()) {
                rule = rule.replaceAll("(?i)<i.*?</i>", ""); // Ignoring reminder text in italic
                if (!mana.isBlack() && rule.matches(regexBlack)) {
                    mana.setBlack(true);
                }
                if (!mana.isBlue() && rule.matches(regexBlue)) {
                    mana.setBlue(true);
                }
                if (!mana.isGreen() && rule.matches(regexGreen)) {
                    mana.setGreen(true);
                }
                if (!mana.isRed() && rule.matches(regexRed)) {
                    mana.setRed(true);
                }
                if (!mana.isWhite() && rule.matches(regexWhite)) {
                    mana.setWhite(true);
                }
            }
        }

        return mana;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        card.setZone(zone, game);
    }

    @Override
    public void setSpellAbility(SpellAbility ability) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public boolean isCountered() {
        return countered;
    }

    public boolean isResolving() {
        return resolving;
    }

    @Override
    public void checkForCountersToAdd(Permanent permanent, Game game) {
        card.checkForCountersToAdd(permanent, game);
    }

    @Override
    public StackObject createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets) {
        Spell copy = this.copySpell(newControllerId);
        game.getState().setZone(copy.getId(), Zone.STACK); // required for targeting ex: Nivmagus Elemental
        game.getStack().push(copy);
        if (chooseNewTargets) {
            copy.chooseNewTargets(game, newControllerId);
        }
        game.fireEvent(new GameEvent(EventType.COPIED_STACKOBJECT, copy.getId(), this.getId(), newControllerId));
        return copy;
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
    public List<UUID> getAttachments() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAttachment(UUID permanentId, Game game) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAttachment(UUID permanentId, Game game) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setCommandedBy(UUID playerId) {
        this.commandedBy = playerId;
    }

    public UUID getCommandedBy() {
        return commandedBy;
    }

}
