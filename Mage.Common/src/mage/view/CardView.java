/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.view;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Modes;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.MageObjectType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.target.Target;
import mage.target.Targets;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardView extends SimpleCardView {

    private static final long serialVersionUID = 1L;

    protected UUID parentId;
    protected String name;
    protected String displayName;
    protected List<String> rules;
    protected String power;
    protected String toughness;
    protected String loyalty;
    protected List<CardType> cardTypes;
    protected List<String> subTypes;
    protected List<String> superTypes;
    protected ObjectColor color;
    protected List<String> manaCost;
    protected int convertedManaCost;
    protected Rarity rarity;

    protected MageObjectType mageObjectType = MageObjectType.NULL;

    protected boolean isAbility;
    protected AbilityType abilityType;
    protected boolean isToken;

    protected CardView ability;
    protected int type;

    protected boolean canTransform;
    protected CardView secondCardFace;
    protected boolean transformed;

    protected boolean flipCard;
    protected boolean faceDown;

    protected String alternateName;
    protected String originalName;

    protected boolean isSplitCard;
    protected String leftSplitName;
    protected ManaCosts leftSplitCosts;
    protected List<String> leftSplitRules;
    protected String rightSplitName;
    protected ManaCosts rightSplitCosts;
    protected List<String> rightSplitRules;

    protected List<UUID> targets;

    protected UUID pairedCard;
    protected boolean paid;
    protected List<CounterView> counters;

    protected boolean controlledByOwner = true;

    protected boolean rotate;
    protected boolean hideInfo; // controls if the tooltip window is shown (eg. controlled face down morph card)

    protected boolean isPlayable;
    protected boolean isChoosable;
    protected boolean selected;
    protected boolean canAttack;

    public CardView(Card card) {
        this(card, null, false);
    }

    public CardView(Card card, UUID cardId) {
        this(card, null, false);
        this.id = cardId;
    }

    public CardView(Card card, Game game, UUID cardId) {
        this(card, game, false);
        this.id = cardId;
    }

    /**
     *
     * @param card
     * @param game
     * @param controlled is the card view created for the card controller - used
     * for morph / face down cards to know which player may see information for
     * the card
     */
    public CardView(Card card, Game game, boolean controlled) {
        this(card, game, controlled, false);
    }

    /**
     *
     * @param card
     * @param game
     * @param controlled is the card view created for the card controller - used
     * for morph / face down cards to know which player may see information for
     * the card
     * @param showFaceDownCard if true and the card is not on the battelfield,
     * also a face down card is shown in the view down cards will be shown
     */
    public CardView(Card card, Game game, boolean controlled, boolean showFaceDownCard) {
        super(card.getId(), card.getExpansionSetCode(), card.getCardNumber(), card.getUsesVariousArt(), card.getTokenSetCode(), game != null);
        // no information available for face down cards as long it's not a controlled face down morph card
        // TODO: Better handle this in Framework (but currently I'm not sure how to do it there) LevelX2
        boolean showFaceUp = true;
        if (game != null) {
            if (card.isFaceDown(game)) {
                showFaceUp = false;
                if (!Zone.BATTLEFIELD.equals(game.getState().getZone(card.getId()))) {
                    if (showFaceDownCard) {
                        showFaceUp = true;
                    }
                }
            }
        }
        //  boolean showFaceUp = game == null || !card.isFaceDown(game) || (!game.getState().getZone(card.getId()).equals(Zone.BATTLEFIELD) && showFaceDownCard);

        if (!showFaceUp) {
            this.fillEmpty(card, controlled);
            if (card instanceof Spell) {
                // special handling for casting of Morph cards
                if (controlled) {
                    this.name = card.getName();
                    this.displayName = card.getName();
                    this.alternateName = card.getName();
                }
                this.power = "2";
                this.toughness = "2";
                this.rules.add("You may cast this card as a 2/2 face-down creature, with no text,"
                        + " no name, no subtypes, and no mana cost by paying {3} rather than paying its mana cost.");
                return;
            } else {
                if (card instanceof Permanent) {
                    this.power = Integer.toString(card.getPower().getValue());
                    this.toughness = Integer.toString(card.getToughness().getValue());
                    this.cardTypes = card.getCardType();
                    this.faceDown = ((Permanent) card).isFaceDown(game);
                } else {
                    // this.hideInfo = true;
                    return;
                }
            }
        }

        SplitCard splitCard = null;
        if (card.isSplitCard()) {
            splitCard = (SplitCard) card;
            rotate = true;
        } else {
            if (card instanceof Spell) {
                switch (((Spell) card).getSpellAbility().getSpellAbilityType()) {
                    case SPLIT_FUSED:
                        splitCard = (SplitCard) ((Spell) card).getCard();
                        rotate = true;
                        break;
                    case SPLIT_LEFT:
                    case SPLIT_RIGHT:
                        rotate = true;
                        break;
                }
            }
        }
        if (splitCard != null) {
            this.isSplitCard = true;
            leftSplitName = splitCard.getLeftHalfCard().getName();
            leftSplitCosts = splitCard.getLeftHalfCard().getManaCost();
            leftSplitRules = splitCard.getLeftHalfCard().getRules(game);
            rightSplitName = splitCard.getRightHalfCard().getName();
            rightSplitCosts = splitCard.getRightHalfCard().getManaCost();
            rightSplitRules = splitCard.getRightHalfCard().getRules(game);
        }

        this.name = card.getImageName();
        this.displayName = card.getName();
        if (game == null) {
            this.rules = card.getRules();
        } else {
            this.rules = card.getRules(game);
        }
        this.manaCost = card.getManaCost().getSymbols();
        this.convertedManaCost = card.getManaCost().convertedManaCost();

        if (card instanceof Permanent) {
            this.mageObjectType = MageObjectType.PERMANENT;
            Permanent permanent = (Permanent) card;
            this.loyalty = Integer.toString(permanent.getCounters().getCount(CounterType.LOYALTY));
            this.pairedCard = permanent.getPairedCard();
            if (!permanent.getControllerId().equals(permanent.getOwnerId())) {
                controlledByOwner = false;
            }
            if (game != null && permanent.getCounters() != null && !permanent.getCounters().isEmpty()) {
                counters = new ArrayList<>();
                for (Counter counter : permanent.getCounters().values()) {
                    counters.add(new CounterView(counter));
                }
            }
        } else {
            if (card.isCopy()) {
                this.mageObjectType = MageObjectType.COPY_CARD;
            } else {
                this.mageObjectType = MageObjectType.CARD;
            }
            this.loyalty = "";
            if (game != null && card.getCounters(game) != null && !card.getCounters(game).isEmpty()) {
                counters = new ArrayList<>();
                for (Counter counter : card.getCounters(game).values()) {
                    counters.add(new CounterView(counter));
                }
            }
        }
        this.power = Integer.toString(card.getPower().getValue());
        this.toughness = Integer.toString(card.getToughness().getValue());
        this.cardTypes = card.getCardType();
        this.subTypes = card.getSubtype();
        this.superTypes = card.getSupertype();
        this.color = card.getColor(game);
        this.canTransform = card.canTransform();
        this.flipCard = card.isFlipCard();
        this.faceDown = !showFaceUp;

        if (card instanceof PermanentToken) {
            this.isToken = true;
            this.mageObjectType = MageObjectType.TOKEN;
            this.rarity = Rarity.COMMON;
            if (((PermanentToken) card).getToken().getOriginalCardNumber() > 0) {
                // a token copied from permanent
                this.expansionSetCode = ((PermanentToken) card).getToken().getOriginalExpansionSetCode();
                this.cardNumber = ((PermanentToken) card).getToken().getOriginalCardNumber();
            } else {
                // a created token
                this.expansionSetCode = ((PermanentToken) card).getExpansionSetCode();
            }
            //
            // set code und card number for token copies to get the image
            this.rules = ((PermanentToken) card).getRules(game);
            this.type = ((PermanentToken) card).getToken().getTokenType();
        } else {
            this.rarity = card.getRarity();
            this.isToken = false;
        }

        if (card.getSecondCardFace() != null) {
            this.secondCardFace = new CardView(card.getSecondCardFace());
            this.alternateName = secondCardFace.getName();
            this.originalName = card.getName();
        }
        this.flipCard = card.isFlipCard();
        if (card.isFlipCard() && card.getFlipCardName() != null) {
            this.alternateName = card.getFlipCardName();
            this.originalName = card.getName();
        }

        if (card instanceof Spell) {
            this.mageObjectType = MageObjectType.SPELL;
            Spell spell = (Spell) card;
            for (SpellAbility spellAbility : spell.getSpellAbilities()) {
                for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
                    spellAbility.getModes().setActiveMode(modeId);
                    if (spellAbility.getTargets().size() > 0) {
                        setTargets(spellAbility.getTargets());
                    }
                }
            }
            // show for modal spell, which mode was choosen
            if (spell.getSpellAbility().isModal()) {
                Modes modes = spell.getSpellAbility().getModes();
                for (UUID modeId : modes.getSelectedModes()) {
                    modes.setActiveMode(modeId);
                    this.rules.add("<span color='green'><i>Chosen mode: " + spell.getSpellAbility().getEffects().getText(modes.get(modeId)) + "</i></span>");
                }
            }
        }
    }

    public CardView(MageObject object) {
        super(object.getId(), "", 0, false, "", true);
        this.name = object.getName();
        this.displayName = object.getName();
        if (object instanceof Permanent) {
            this.mageObjectType = MageObjectType.PERMANENT;
            this.power = Integer.toString(object.getPower().getValue());
            this.toughness = Integer.toString(object.getToughness().getValue());
            this.loyalty = Integer.toString(((Permanent) object).getCounters().getCount(CounterType.LOYALTY));
        } else {
            this.power = object.getPower().toString();
            this.toughness = object.getToughness().toString();
            this.loyalty = "";
        }
        this.cardTypes = object.getCardType();
        this.subTypes = object.getSubtype();
        this.superTypes = object.getSupertype();
        this.color = object.getColor(null);
        this.manaCost = object.getManaCost().getSymbols();
        this.convertedManaCost = object.getManaCost().convertedManaCost();
        if (object instanceof PermanentToken) {
            this.mageObjectType = MageObjectType.TOKEN;
            PermanentToken permanentToken = (PermanentToken) object;
            this.rarity = Rarity.COMMON;
            this.expansionSetCode = permanentToken.getExpansionSetCode();
            this.rules = permanentToken.getRules();
            this.type = permanentToken.getToken().getTokenType();
        } else if (object instanceof Emblem) {
            this.mageObjectType = MageObjectType.EMBLEM;
            Emblem emblem = (Emblem) object;
            this.rarity = Rarity.SPECIAL;
            this.rules = emblem.getAbilities().getRules(emblem.getName());
        }
        if (this.rarity == null && object instanceof StackAbility) {
            StackAbility stackAbility = (StackAbility) object;
            this.rarity = Rarity.NA;
            this.rules = new ArrayList<>();
            this.rules.add(stackAbility.getRule());
            if (stackAbility.getZone().equals(Zone.COMMAND)) {
                this.expansionSetCode = stackAbility.getExpansionSetCode();
            }
        }
    }

    protected CardView() {
        super(null, "", 0, false, "", true);
    }

    public CardView(EmblemView emblem) {
        this(true);
        this.gameObject = true;
        this.id = emblem.getId();
        this.mageObjectType = MageObjectType.EMBLEM;
        this.name = emblem.getName();
        this.displayName = name;
        this.rules = emblem.getRules();
        // emblem images are always with common (black) symbol
        this.expansionSetCode = emblem.getExpansionSetCode();
        this.rarity = Rarity.COMMON;
    }

    public CardView(boolean empty) {
        super(null, "", 0, false, "");
        if (!empty) {
            throw new IllegalArgumentException("Not supported.");
        }
        fillEmpty(null, false);
    }

    private void fillEmpty(Card card, boolean controlled) {
        this.name = "Face Down";
        this.displayName = name;
        this.rules = new ArrayList<>();
        this.power = "";
        this.toughness = "";
        this.loyalty = "";
        this.cardTypes = new ArrayList<>();
        this.subTypes = new ArrayList<>();
        this.superTypes = new ArrayList<>();
        this.color = new ObjectColor();
        this.manaCost = new ArrayList<>();
        this.convertedManaCost = 0;

        // the controller can see more information (e.g. enlarged image) than other players for face down cards (e.g. Morph played face down)
        if (!controlled) {
            this.rarity = Rarity.COMMON;
            this.expansionSetCode = "";
            this.cardNumber = 0;
        } else {
            this.rarity = card.getRarity();
        }

        if (card != null) {
            if (card instanceof Permanent) {
                this.mageObjectType = MageObjectType.PERMANENT;
            } else {
                if (card.isCopy()) {
                    this.mageObjectType = MageObjectType.COPY_CARD;
                } else {
                    this.mageObjectType = MageObjectType.CARD;
                }
            }
            if (card instanceof PermanentToken) {
                this.mageObjectType = MageObjectType.TOKEN;
            }
            if (card instanceof Spell) {
                this.mageObjectType = MageObjectType.SPELL;
            }
        }

    }

    CardView(Token token) {
        super(token.getId(), "", 0, false, "");
        this.isToken = true;
        this.id = token.getId();
        this.name = token.getName();
        this.displayName = token.getName();
        this.rules = token.getAbilities().getRules(this.name);
        this.power = token.getPower().toString();
        this.toughness = token.getToughness().toString();
        this.loyalty = "";
        this.cardTypes = token.getCardType();
        this.subTypes = token.getSubtype();
        this.superTypes = token.getSupertype();
        this.color = token.getColor(null);
        this.manaCost = token.getManaCost().getSymbols();
        this.rarity = Rarity.NA;
        this.type = token.getTokenType();
        this.tokenSetCode = token.getOriginalExpansionSetCode();
    }

    protected final void setTargets(Targets targets) {
        for (Target target : targets) {
            if (target.isChosen()) {
                for (UUID targetUUID : target.getTargets()) {
                    if (this.targets == null) {
                        this.targets = new ArrayList<>();
                    }
                    this.targets.add(targetUUID);
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getRules() {
        return rules;
    }

    public void overrideRules(List<String> rules) {
        this.rules = rules;
    }

    public void setIsAbility(boolean isAbility) {
        this.isAbility = isAbility;
    }

    public boolean isAbility() {
        return isAbility;
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(AbilityType abilityType) {
        this.abilityType = abilityType;
    }

    public String getPower() {
        return power;
    }

    public String getToughness() {
        return toughness;
    }

    public String getLoyalty() {
        return loyalty;
    }

    public List<CardType> getCardTypes() {
        return cardTypes;
    }

    public List<String> getSubTypes() {
        return subTypes;
    }

    public List<String> getSuperTypes() {
        return superTypes;
    }

    public ObjectColor getColor() {
        return color;
    }

    public List<String> getManaCost() {
        return manaCost;
    }

    public int getConvertedManaCost() {
        return convertedManaCost;
    }

    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public String getExpansionSetCode() {
        return expansionSetCode;
    }

    public void setExpansionSetCode(String expansionSetCode) {
        this.expansionSetCode = expansionSetCode;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int getCardNumber() {
        return cardNumber;
    }

    /**
     * Returns UUIDs for targets. Can be null if there is no target selected.
     *
     * @return
     */
    public List<UUID> getTargets() {
        return targets;
    }

    public void overrideTargets(List<UUID> newTargets) {
        this.targets = newTargets;
    }

    public void overrideId(UUID id) {
        if (parentId == null) {
            parentId = this.id;
        }
        this.id = id;
    }

    public UUID getParentId() {
        if (parentId != null) {
            return parentId;
        }
        return id;
    }

    public void setAbility(CardView ability) {
        this.ability = ability;
    }

    public CardView getAbility() {
        return this.ability;
    }

    @Override
    public String toString() {
        return getName() + " [" + getId() + "]";
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public boolean canTransform() {
        return this.canTransform;
    }

    public boolean isSplitCard() {
        return this.isSplitCard;
    }

    /**
     * Name of the other side (transform), flipped, or copying card name.
     *
     * @return name
     */
    public String getAlternateName() {
        return alternateName;
    }

    /**
     * Stores the name of the original name, to provide it for a flipped or
     * transformed or copying card
     *
     * @return
     */
    public String getOriginalName() {
        return originalName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getLeftSplitName() {
        return leftSplitName;
    }

    public ManaCosts getLeftSplitCosts() {
        return leftSplitCosts;
    }

    public List<String> getLeftSplitRules() {
        return leftSplitRules;
    }

    public String getRightSplitName() {
        return rightSplitName;
    }

    public ManaCosts getRightSplitCosts() {
        return rightSplitCosts;
    }

    public List<String> getRightSplitRules() {
        return rightSplitRules;
    }

    public CardView getSecondCardFace() {
        return this.secondCardFace;
    }

    public boolean isToken() {
        return this.isToken;
    }

    public void setTransformed(boolean transformed) {
        this.transformed = transformed;
    }

    public boolean isTransformed() {
        return this.transformed;
    }

    public UUID getPairedCard() {
        return pairedCard;
    }

    public int getType() {
        return type;
    }

    public MageObjectType getMageObjectType() {
        return mageObjectType;
    }

    public void setMageObjectType(MageObjectType mageObjectType) {
        this.mageObjectType = mageObjectType;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public List<CounterView> getCounters() {
        return counters;
    }

    public boolean isControlledByOwner() {
        return controlledByOwner;
    }

    public boolean isFlipCard() {
        return flipCard;
    }

    public boolean isToRotate() {
        return rotate;
    }

    public boolean hideInfo() {
        return hideInfo;
    }

    public boolean isPlayable() {
        return isPlayable;
    }

    public void setPlayable(boolean isPlayable) {
        this.isPlayable = isPlayable;
    }

    public boolean isChoosable() {
        return isChoosable;
    }

    public void setChoosable(boolean isChoosable) {
        this.isChoosable = isChoosable;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

}
