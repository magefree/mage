package mage.game.stack;

import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.*;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.mana.ActivationManaAbilityStep;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.FilterMana;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.GameState;
import mage.game.MageObjectAttribute;
import mage.game.events.CopiedStackObjectEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.GameLog;
import mage.util.ManaUtil;
import mage.util.SubTypes;
import mage.util.functions.StackObjectCopyApplier;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Spell extends StackObjectImpl implements Card {

    private static final Logger logger = Logger.getLogger(Spell.class);

    private final List<SpellAbility> spellAbilities = new ArrayList<>();

    private final Card card;
    private final ObjectColor color;
    private final ObjectColor frameColor;
    private final FrameStyle frameStyle;
    private final SpellAbility ability;
    private final Zone fromZone;
    private final UUID id;
    protected int zoneChangeCounter; // spell's ZCC must be synced with card's on stack or another copied spell

    private UUID controllerId;
    private boolean copy;
    private MageObject copyFrom; // copied card INFO (used to call original adjusters)
    private boolean faceDown;
    private boolean countered;
    private boolean resolving = false;
    private UUID commandedBy = null; // for Word of Command
    private int startingLoyalty;

    private ActivationManaAbilityStep currentActivatingManaAbilitiesStep = ActivationManaAbilityStep.BEFORE;

    public Spell(Card card, SpellAbility ability, UUID controllerId, Zone fromZone, Game game) {
        this(card, ability, controllerId, fromZone, game, false);
    }

    private Spell(Card card, SpellAbility ability, UUID controllerId, Zone fromZone, Game game, boolean isCopy) {
        Card affectedCard = card;

        // TODO: must be removed after transform cards (one side) migrated to MDF engine (multiple sides)
        if (ability.getSpellAbilityCastMode() == SpellAbilityCastMode.DISTURB && affectedCard.getSecondCardFace() != null) {
            // simulate another side as new card (another code part in continues effect from disturb ability)
            affectedCard = TransformAbility.transformCardSpellStatic(card, card.getSecondCardFace(), game);
        }

        this.card = affectedCard;
        this.color = affectedCard.getColor(null).copy();
        this.frameColor = affectedCard.getFrameColor(null).copy();
        this.frameStyle = affectedCard.getFrameStyle();
        this.startingLoyalty = affectedCard.getStartingLoyalty();
        this.id = ability.getId();
        this.zoneChangeCounter = affectedCard.getZoneChangeCounter(game); // sync card's ZCC with spell (copy spell settings)
        this.ability = ability;
        this.ability.setControllerId(controllerId);
        if (ability.getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            // if this spell is going to be a copy, these abilities will be copied in copySpell
            if (!isCopy) {
                SpellAbility left = ((SplitCard) affectedCard).getLeftHalfCard().getSpellAbility().copy();
                SpellAbility right = ((SplitCard) affectedCard).getRightHalfCard().getSpellAbility().copy();
                left.setSourceId(ability.getSourceId());
                right.setSourceId(ability.getSourceId());
                spellAbilities.add(left);
                spellAbilities.add(right);
            }
        } else {
            spellAbilities.add(ability);
        }
        this.controllerId = controllerId;
        this.fromZone = fromZone;
        this.countered = false;
    }

    public Spell(final Spell spell) {
        this.id = spell.id;
        this.zoneChangeCounter = spell.zoneChangeCounter;
        for (SpellAbility spellAbility : spell.spellAbilities) {
            this.spellAbilities.add(spellAbility.copy());
        }
        if (spell.spellAbilities.get(0).equals(spell.ability)) {
            this.ability = this.spellAbilities.get(0);
        } else {
            this.ability = spell.ability.copy();
        }
        this.card = spell.card.copy();

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

        this.currentActivatingManaAbilitiesStep = spell.currentActivatingManaAbilitiesStep;
        this.targetChanged = spell.targetChanged;
        this.startingLoyalty = spell.startingLoyalty;
    }

    public boolean activate(Game game, boolean noMana) {
        setCurrentActivatingManaAbilitiesStep(ActivationManaAbilityStep.BEFORE); // mana payment step started, can use any mana abilities, see AlternateManaPaymentAbility

        if (!ability.activate(game, noMana)) {
            return false;
        }

        // spell can contains multiple abilities to activate (fused split, splice)
        for (SpellAbility spellAbility : spellAbilities) {
            if (ability.equals(spellAbility)) {
                // activated first
                continue;
            }

            boolean payNoMana = noMana;
            // costs for spliced abilities were added to main spellAbility, so pay no mana for spliced abilities
            payNoMana |= spellAbility.getSpellAbilityType() == SpellAbilityType.SPLICE;
            // costs for fused ability pay on first spell activate, so all parts must be without mana
            // see https://github.com/magefree/mage/issues/6603
            payNoMana |= ability.getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED;

            if (!spellAbility.activate(game, payNoMana)) {
                return false;
            }
        }
        setCurrentActivatingManaAbilitiesStep(ActivationManaAbilityStep.NORMAL);
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

        if (card instanceof AdventureCardSpell) {
            AdventureCard adventureCard = ((AdventureCardSpell) card).getParentCard();
            return GameLog.replaceNameByColoredName(card, getSpellAbility().toString(), adventureCard)
                    + " as Adventure spell of " + GameLog.getColoredObjectIdName(adventureCard);
        }

        if (card instanceof ModalDoubleFacesCardHalf) {
            ModalDoubleFacesCard mdfCard = (ModalDoubleFacesCard) card.getMainCard();
            return GameLog.replaceNameByColoredName(card, getSpellAbility().toString(), mdfCard)
                    + " as mdf side of " + GameLog.getColoredObjectIdName(mdfCard);
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
        if (this.isInstantOrSorcery(game)) {
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
                    // legality of targets is checked only as the spell begins to resolve, not in between modes (spliced spells handeled correctly?)
                    if (spellAbilityCheckTargetsAndDeactivateModes(spellAbility, game)) {
                        for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
                            spellAbility.getModes().setActiveMode(modeId);
                            result |= spellAbility.resolve(game);
                        }
                        index++;
                    }
                }
                if (game.getState().getZone(card.getMainCard().getId()) == Zone.STACK) {
                    if (isCopy()) {
                        // copied spell, only remove from stack
                        game.getStack().remove(this, game);
                    } else {
                        controller.moveCards(card, Zone.GRAVEYARD, ability, game);
                    }
                }
                return result;
            }
            //20091005 - 608.2b
            if (!game.isSimulation()) {
                game.informPlayers(getName() + " has been fizzled.");
            }
            counter(null, /*this.getSpellAbility()*/ game);
            return false;
        } else if (this.isEnchantment(game) && this.hasSubtype(SubType.AURA, game)) {
            if (ability.getTargets().stillLegal(ability, game)) {
                boolean bestow = SpellAbilityCastMode.BESTOW.equals(ability.getSpellAbilityCastMode());
                if (bestow) {
                    // before put to play:
                    // Must be removed first time, after that will be removed by continous effect
                    // Otherwise effects like evolve trigger from creature comes into play event
                    card.removeCardType(CardType.CREATURE);
                    card.addSubType(game, SubType.AURA);
                }
                UUID permId;
                boolean flag;
                if (isCopy()) {
                    EmptyToken token = new EmptyToken();
                    CardUtil.copyTo(token).from(card, game, this);
                    // The token that a resolving copy of a spell becomes isn’t said to have been “created.” (2020-09-25)
                    if (token.putOntoBattlefield(1, game, ability, getControllerId(), false, false, null, false)) {
                        permId = token.getLastAddedTokenIds().stream().findFirst().orElse(null);
                        flag = true;
                    } else {
                        permId = null;
                        flag = false;
                    }
                } else {
                    permId = card.getId();
                    flag = controller.moveCards(card, Zone.BATTLEFIELD, ability, game, false, faceDown, false, null);
                }
                if (flag) {
                    if (bestow) {
                        // card will be copied during putOntoBattlefield, so the card of CardPermanent has to be changed
                        // TODO: Find a better way to prevent bestow creatures from being effected by creature affecting abilities
                        Permanent permanent = game.getPermanent(permId);
                        if (permanent instanceof PermanentCard) {
                            // after put to play:
                            // restore removed stats (see "before put to play" above)
                            permanent.setSpellAbility(ability); // otherwise spell ability without bestow will be set
                            card.addCardType(CardType.CREATURE);
                            card.getSubtype().remove(SubType.AURA);
                        }
                    }
                    if (isCopy()) {
                        Permanent token = game.getPermanent(permId);
                        if (token == null) {
                            return false;
                        }
                        for (Ability ability2 : token.getAbilities()) {
                            if (!bestow || ability2 instanceof BestowAbility) {
                                ability2.getTargets().get(0).add(ability.getFirstTarget(), game);
                                ability2.getEffects().get(0).apply(game, ability2);
                                return ability2.resolve(game);
                            }
                        }
                        return false;
                    }
                    return ability.resolve(game);
                }
                if (bestow) {
                    card.addCardType(game, CardType.CREATURE);
                }
                return false;
            }
            // Aura has no legal target and its a bestow enchantment -> Add it to battlefield as creature
            if (SpellAbilityCastMode.BESTOW.equals(this.getSpellAbility().getSpellAbilityCastMode())) {
                if (controller.moveCards(card, Zone.BATTLEFIELD, ability, game, false, faceDown, false, null)) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent instanceof PermanentCard) {
                        ((PermanentCard) permanent).getCard().addCardType(game, CardType.CREATURE);
                        ((PermanentCard) permanent).getCard().removeSubType(game, SubType.AURA);
                        return true;
                    }
                }
                return false;
            } else {
                //20091005 - 608.2b
                if (!game.isSimulation()) {
                    game.informPlayers(getName() + " has been fizzled.");
                }
                counter(null, /*this.getSpellAbility()*/ game);
                return false;
            }
        } else if (isCopy()) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(card, game, this);
            // The token that a resolving copy of a spell becomes isn’t said to have been “created.” (2020-09-25)
            token.putOntoBattlefield(1, game, ability, getControllerId(), false, false, null, false);
            return true;
        } else {
            return controller.moveCards(card, Zone.BATTLEFIELD, ability, game, false, faceDown, false, null);
        }
    }

    private boolean hasTargets(SpellAbility spellAbility, Game game) {
        if (spellAbility.getModes().getSelectedModes().size() < 2) {
            return !spellAbility.getTargets().isEmpty();
        }
        for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
            if (!spellAbility.getModes().get(modeId).getTargets().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Legality of the targets of all modes are only checked as the spell begins
     * to resolve A mode without any legal target (if it has targets at all)
     * won't resolve. So modes with targets without legal targets are
     * unselected.
     *
     * @param spellAbility
     * @param game
     * @return
     */
    private boolean spellAbilityCheckTargetsAndDeactivateModes(SpellAbility spellAbility, Game game) {
        boolean legalModes = false;
        for (Iterator<UUID> iterator = spellAbility.getModes().getSelectedModes().iterator(); iterator.hasNext(); ) {
            UUID nextSelectedModeId = iterator.next();
            Mode mode = spellAbility.getModes().get(nextSelectedModeId);
            if (!mode.getTargets().isEmpty()) {
                if (!mode.getTargets().stillLegal(spellAbility, game)) {
                    spellAbility.getModes().removeSelectedMode(mode.getId());
                    iterator.remove();
                    continue;
                }
            }
            legalModes = true;
        }
        return legalModes;
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

    @Override
    public void counter(Ability source, Game game) {
        this.counter(source, game, PutCards.GRAVEYARD);
    }

    @Override
    public void counter(Ability source, Game game, PutCards zone) {
        // source can be null for fizzled spells, don't use that code in your ZONE_CHANGE watchers/triggers:
        // event.getSourceId().equals
        // TODO: fizzled spells are no longer considered "countered" as of current rules; may need refactor
        this.countered = true;
        if (isCopy()) {
            // copied spell, only remove from stack
            game.getStack().remove(this, game);
            return;
        }
        Player player = game.getPlayer(source == null ? getControllerId() : source.getControllerId());
        if (player != null) {
            switch (zone) {
                case TOP_OR_BOTTOM:
                    if (player.chooseUse(Outcome.Detriment,
                            "Put the countered spell on the top or bottom of its owner's library?",
                            null, "Top", "Bottom", source, game
                    )) {
                        player.putCardsOnTopOfLibrary(new CardsImpl(card), game, source, false);
                    } else {
                        player.putCardsOnBottomOfLibrary(new CardsImpl(card), game, source, false);
                    }
                    break;
                case TOP_ANY:
                    player.putCardsOnTopOfLibrary(new CardsImpl(card), game, source, false);
                    break;
                case BOTTOM_ANY:
                case BOTTOM_RANDOM:
                    player.putCardsOnBottomOfLibrary(new CardsImpl(card), game, source, false);
                    break;
                case BATTLEFIELD_TAPPED:
                    player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                    break;
                default:
                    player.moveCards(card, zone.getZone(), source, game);
            }
        }
    }

    public ActivationManaAbilityStep getCurrentActivatingManaAbilitiesStep() {
        return this.currentActivatingManaAbilitiesStep;
    }

    public void setCurrentActivatingManaAbilitiesStep(ActivationManaAbilityStep currentActivatingManaAbilitiesStep) {
        this.currentActivatingManaAbilitiesStep = currentActivatingManaAbilitiesStep;
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
            if (card instanceof AdventureCardSpell) {
                idName = ((AdventureCardSpell) card).getParentCard().getId().toString().substring(0, 3);
            } else {
                idName = card.getId().toString().substring(0, 3);
            }
        } else {
            idName = getId().toString().substring(0, 3);
        }
        return getName() + " [" + idName + ']';
    }

    @Override
    public String getLogName() {
        if (faceDown) {
            return "face down spell";
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
    public List<CardType> getCardType(Game game) {
        if (faceDown) {
            List<CardType> cardTypes = new ArrayList<>();
            cardTypes.add(CardType.CREATURE);
            return cardTypes;
        }
        if (SpellAbilityCastMode.BESTOW.equals(this.getSpellAbility().getSpellAbilityCastMode())) {
            List<CardType> cardTypes = new ArrayList<>();
            cardTypes.addAll(card.getCardType(game));
            cardTypes.remove(CardType.CREATURE);
            return cardTypes;
        }
        return card.getCardType(game);
    }

    @Override
    public SubTypes getSubtype() {
        return card.getSubtype();
    }

    @Override
    public SubTypes getSubtype(Game game) {
        if (SpellAbilityCastMode.BESTOW.equals(this.getSpellAbility().getSpellAbilityCastMode())) {
            SubTypes subtypes = card.getSubtype(game);
            if (!subtypes.contains(SubType.AURA)) { // do it only once
                subtypes.add(SubType.AURA);
            }
            return subtypes;
        }
        return card.getSubtype(game);
    }

    @Override
    public boolean hasSubtype(SubType subtype, Game game) {
        if (SpellAbilityCastMode.BESTOW.equals(this.getSpellAbility().getSpellAbilityCastMode())) { // workaround for Bestow (don't like it)
            SubTypes subtypes = card.getSubtype(game);
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
    public Abilities<Ability> getInitAbilities() {
        return new AbilitiesImpl<>();
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        return card.getAbilities(game);
    }

    @Override
    public boolean hasAbility(Ability ability, Game game) {
        return card.hasAbility(ability, game);
    }

    @Override
    public ObjectColor getColor() {
        return color;
    }

    @Override
    public ObjectColor getColor(Game game) {
        if (game != null) {
            MageObjectAttribute mageObjectAttribute = game.getState().getMageObjectAttribute(getId());
            if (mageObjectAttribute != null) {
                return mageObjectAttribute.getColor();
            }
        }
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
    public int getManaValue() {
        int cmc = 0;
        if (faceDown) {
            return 0;
        }
        for (SpellAbility spellAbility : spellAbilities) {
            cmc += spellAbility.getConvertedXManaCost(getCard());
        }
        cmc += getCard().getManaCost().manaValue();
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
        return this.startingLoyalty;
    }

    @Override
    public void setStartingLoyalty(int startingLoyalty) {
        this.startingLoyalty = startingLoyalty;
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

    @Override
    public void addAbility(Ability ability) {
        throw new UnsupportedOperationException("Not supported.");
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
    public boolean turnFaceUp(Ability source, Game game, UUID playerId) {
        setFaceDown(false, game);
        return true;
    }

    @Override
    public boolean turnFaceDown(Ability source, Game game, UUID playerId) {
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
    public boolean isTransformable() {
        return false;
    }

    @Override
    public Card getSecondCardFace() {
        return null;
    }

    @Override
    public SpellAbility getSecondFaceSpellAbility() {
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

    /**
     * Copy current spell on stack, but do not put copy back to stack (you can modify and put it later)
     * <p>
     * Warning, don't forget to call CopyStackObjectEvent and CopiedStackObjectEvent before and after copy
     * CopyStackObjectEvent can change new copies amount, see Twinning Staff
     * <p>
     * Warning, don't forget to call spell.setZone before push to stack
     *
     * @param game
     * @param newController controller of the copied spell
     * @return
     */
    public Spell copySpell(Game game, Ability source, UUID newController) {
        // copied spells must use copied cards
        // spell can be from card's part (mdf/adventure), but you must copy FULL card
        Card copiedMainCard = game.copyCard(this.card.getMainCard(), source, newController);
        // find copied part
        Map<UUID, MageObject> mapOldToNew = CardUtil.getOriginalToCopiedPartsMap(this.card.getMainCard(), copiedMainCard);
        if (!mapOldToNew.containsKey(this.card.getId())) {
            throw new IllegalStateException("Can't find card id after main card copy: " + copiedMainCard.getName());
        }
        Card copiedPart = (Card) mapOldToNew.get(this.card.getId());

        // copy spell
        Spell spellCopy = new Spell(copiedPart, this.ability.copySpell(this.card, copiedPart), this.controllerId, this.fromZone, game, true);
        UUID copiedSourceId = spellCopy.ability.getSourceId();

        // non-fused spell:
        //    this.spellAbilities.get(0) is alias (NOT copy) of this.ability
        //    this.spellAbilities.get(1) is first spliced card (if any)
        // fused spell:
        //    this.spellAbilities.get(0) is left half
        //    this.spellAbilities.get(1) is right half
        //    this.spellAbilities.get(2) is first spliced card (if any)
        // for non-fused spell, ability was already added to spellAbilities in constructor and must not be copied again
        // for fused spell, all of spellAbilities must be copied here
        boolean skipFirst = (this.ability.getSpellAbilityType() != SpellAbilityType.SPLIT_FUSED);
        for (SpellAbility spellAbility : this.getSpellAbilities()) {
            if (skipFirst) {
                skipFirst = false;
                continue;
            }
            SpellAbility newAbility = spellAbility.copy(); // e.g. spliced spell
            newAbility.newId();
            newAbility.setSourceId(copiedSourceId);
            spellCopy.addSpellAbility(newAbility);
        }
        spellCopy.setCopy(true, this);
        spellCopy.setControllerId(newController);
        spellCopy.syncZoneChangeCounterOnStack(this, game);
        return spellCopy;
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, Ability source) {
        return card.removeFromZone(game, fromZone, source);
    }

    @Override
    public boolean moveToZone(Zone zone, Ability source, Game game, boolean flag) {
        return moveToZone(zone, source, game, flag, null);
    }

    @Override
    public boolean moveToZone(Zone zone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        // 706.10a If a copy of a spell is in a zone other than the stack, it ceases to exist.
        // If a copy of a card is in any zone other than the stack or the battlefield, it ceases to exist.
        // These are state-based actions. See rule 704.
        if (this.isCopy() && zone != Zone.STACK) {
            return true;
        }
        return card.moveToZone(zone, source, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game) {
        return moveToExile(exileId, name, source, game, null);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        if (this.isCopy()) {
            // copied spell, only remove from stack
            game.getStack().remove(this, game);
            return true;
        }
        return this.card.moveToExile(exileId, name, source, game, appliedEffects);
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, Ability source, UUID controllerId) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, Ability source, UUID controllerId, boolean tapped) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, Ability source, UUID controllerId, boolean tapped, boolean facedown) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, Ability source, UUID controllerId, boolean tapped, boolean facedown, List<UUID> appliedEffects) {
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
    public Ability getStackAbility() {
        return this.ability;
    }

    @Override
    public void assignNewId() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        // spell's zcc can't be changed after put to stack
        return zoneChangeCounter;
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    /**
     * Sync ZCC with card on stack
     *
     * @param card
     * @param game
     */
    public void syncZoneChangeCounterOnStack(Card card, Game game) {
        this.zoneChangeCounter = card.getZoneChangeCounter(game);
    }

    /**
     * Sync ZCC with copy spell on stack
     *
     * @param spell
     * @param game
     */
    public void syncZoneChangeCounterOnStack(Spell spell, Game game) {
        this.zoneChangeCounter = spell.getZoneChangeCounter(game);
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

    /**
     * Game processing a copies as normal cards, so you don't need to check spell's copy for move/exile.
     * Use this only in exceptional situations or to skip unaffected code/choices.
     *
     * @return
     */
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
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game) {
        return card.addCounters(counter, playerAddingCounters, source, game);
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game, boolean isEffect) {
        return card.addCounters(counter, playerAddingCounters, source, game, isEffect);
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game, List<UUID> appliedEffects) {
        return card.addCounters(counter, playerAddingCounters, source, game, appliedEffects);
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game, List<UUID> appliedEffects, boolean isEffect) {
        return card.addCounters(counter, playerAddingCounters, source, game, appliedEffects, isEffect);
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game, List<UUID> appliedEffects, boolean isEffect, int maxCounters) {
        return card.addCounters(counter, playerAddingCounters, source, game, appliedEffects, isEffect, maxCounters);
    }

    @Override
    public void removeCounters(String name, int amount, Ability source, Game game) {
        card.removeCounters(name, amount, source, game);
    }

    @Override
    public void removeCounters(Counter counter, Ability source, Game game) {
        card.removeCounters(counter, source, game);
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
        return ManaUtil.getColorIdentity(this);
    }

    @Override
    public void setZone(Zone zone, Game game) {
        card.setZone(zone, game);
        game.getState().setZone(this.getId(), zone);
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
    public void checkForCountersToAdd(Permanent permanent, Ability source, Game game) {
        card.checkForCountersToAdd(permanent, source, game);
    }

    @Override
    public void createSingleCopy(UUID newControllerId, StackObjectCopyApplier applier, MageObjectReferencePredicate newTargetFilterPredicate, Game game, Ability source, boolean chooseNewTargets) {
        Spell spellCopy = this.copySpell(game, source, newControllerId);
        if (applier != null) {
            applier.modifySpell(spellCopy, game);
        }
        spellCopy.setZone(Zone.STACK, game);  // required for targeting ex: Nivmagus Elemental
        game.getStack().push(spellCopy);

        // new targets
        if (newTargetFilterPredicate != null) {
            spellCopy.chooseNewTargets(game, newControllerId, true, false, newTargetFilterPredicate);
        } else if (chooseNewTargets || applier != null) { // if applier is non-null but predicate is null then it's extra
            spellCopy.chooseNewTargets(game, newControllerId);
        }

        game.fireEvent(new CopiedStackObjectEvent(this, spellCopy, newControllerId));
    }

    @Override
    public boolean isAllCreatureTypes(Game game) {
        return false;
    }

    @Override
    public void setIsAllCreatureTypes(boolean value) {
    }

    @Override
    public void setIsAllCreatureTypes(Game game, boolean value) {
    }

    @Override
    public List<UUID> getAttachments() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAttachment(UUID permanentId, Ability source, Game game) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAttachment(UUID permanentId, Ability source, Game game) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setCommandedBy(UUID playerId) {
        this.commandedBy = playerId;
    }

    public UUID getCommandedBy() {
        return commandedBy;
    }

    @Override
    public void looseAllAbilities(Game game) {
        throw new UnsupportedOperationException("Spells should not loose all abilities. Check if this operation is correct.");
    }

    @Override
    public String toString() {
        return ability.toString();
    }

    @Override
    public List<CardType> getCardTypeForDeckbuilding() {
        throw new UnsupportedOperationException("Must call for cards only.");
    }

    @Override
    public boolean hasCardTypeForDeckbuilding(CardType cardType) {
        return false;
    }

    @Override
    public boolean hasSubTypeForDeckbuilding(SubType subType) {
        return false;
    }
}
