package mage.view;

import com.google.gson.annotations.Expose;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.designations.Designation;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.target.Target;
import mage.target.Targets;
import mage.util.SubTypeList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardView extends SimpleCardView implements SelectableObjectView {

    private static final long serialVersionUID = 1L;

    protected UUID parentId;
    @Expose
    protected String name;
    @Expose
    protected String displayName;
    @Expose
    protected List<String> rules;
    @Expose
    protected String power;
    @Expose
    protected String toughness;
    @Expose
    protected String loyalty = "";
    protected String startingLoyalty;
    protected Set<CardType> cardTypes;
    protected SubTypeList subTypes;
    protected Set<SuperType> superTypes;
    protected ObjectColor color;
    protected ObjectColor frameColor;
    protected FrameStyle frameStyle;
    protected List<String> manaCost;
    protected int convertedManaCost;
    protected Rarity rarity;

    protected MageObjectType mageObjectType = MageObjectType.NULL;

    protected boolean isAbility;
    protected AbilityType abilityType;
    protected boolean isToken;

    protected CardView ability;
    protected int type;

    protected boolean transformable;
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
    protected String leftSplitTypeLine;
    protected String rightSplitName;
    protected ManaCosts rightSplitCosts;
    protected List<String> rightSplitRules;
    protected String rightSplitTypeLine;

    protected ArtRect artRect = ArtRect.NORMAL;

    protected List<UUID> targets;
    protected UUID pairedCard;
    protected List<UUID> bandedCards;
    protected boolean paid;
    protected List<CounterView> counters;

    protected boolean controlledByOwner = true;

    protected Zone zone;

    protected boolean rotate;
    protected boolean hideInfo; // controls if the tooltip window is shown (eg. controlled face down morph card)

    protected boolean isPlayable;
    protected boolean isChoosable;
    protected boolean selected;
    protected boolean canAttack;
    protected boolean canBlock;
    protected boolean inViewerOnly;

    protected Card originalCard = null;

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

    public CardView(CardView cardView) {
        super(cardView.id, cardView.expansionSetCode, cardView.cardNumber, cardView.usesVariousArt, cardView.tokenSetCode, cardView.gameObject, cardView.tokenDescriptor);
        this.originalCard = cardView.originalCard;

        this.id = UUID.randomUUID();
        this.parentId = cardView.parentId;
        this.name = cardView.name;
        this.displayName = cardView.displayName;
        this.rules = cardView.rules;
        this.power = cardView.power;
        this.toughness = cardView.toughness;
        this.loyalty = cardView.loyalty;
        this.startingLoyalty = cardView.startingLoyalty;
        this.cardTypes = cardView.cardTypes;
        this.subTypes = cardView.subTypes;
        this.superTypes = cardView.superTypes;
        this.color = cardView.color;
        this.frameColor = cardView.frameColor;
        this.frameStyle = cardView.frameStyle;
        this.manaCost = cardView.manaCost;
        this.convertedManaCost = cardView.convertedManaCost;
        this.rarity = cardView.rarity;

        this.mageObjectType = cardView.mageObjectType;

        this.isAbility = cardView.isAbility;
        this.abilityType = cardView.abilityType;
        this.isToken = cardView.isToken;

        this.ability = null;
        this.type = cardView.type;

        this.transformable = cardView.transformable;
        if (cardView.secondCardFace != null) {
            this.secondCardFace = new CardView(cardView.secondCardFace);
        } else {
            this.secondCardFace = null;
        }
        this.transformed = cardView.transformed;

        this.flipCard = cardView.flipCard;
        this.faceDown = cardView.faceDown;

        this.alternateName = cardView.alternateName;
        this.originalName = cardView.originalName;
        this.artRect = cardView.artRect;

        this.isSplitCard = cardView.isSplitCard;
        this.leftSplitName = cardView.leftSplitName;
        this.leftSplitCosts = cardView.leftSplitCosts;
        this.leftSplitRules = null;
        this.leftSplitTypeLine = cardView.leftSplitTypeLine;
        this.rightSplitName = cardView.rightSplitName;
        this.rightSplitCosts = cardView.rightSplitCosts;
        this.rightSplitRules = null;
        this.rightSplitTypeLine = cardView.rightSplitTypeLine;

        this.targets = null;

        this.pairedCard = cardView.pairedCard;
        this.bandedCards = null;
        this.paid = cardView.paid;
        this.counters = null;

        this.controlledByOwner = cardView.controlledByOwner;

        this.zone = cardView.zone;

        this.rotate = cardView.rotate;
        this.hideInfo = cardView.hideInfo;

        this.isPlayable = cardView.isPlayable;
        this.isChoosable = cardView.isChoosable;
        this.selected = cardView.selected;
        this.canAttack = cardView.canAttack;
        this.canBlock = cardView.canBlock;
        this.inViewerOnly = cardView.inViewerOnly;
        this.originalCard = cardView.originalCard.copy();
    }

    /**
     * @param card
     * @param game
     * @param controlled is the card view created for the card controller - used
     *                   for morph / face down cards to know which player may see information for
     *                   the card
     */
    public CardView(Card card, Game game, boolean controlled) {
        this(card, game, controlled, false, false);
    }

    private static String getCardTypeLine(Game game, Card card) {
        StringBuilder sbType = new StringBuilder();
        for (SuperType superType : card.getSuperType()) {
            sbType.append(superType).append(' ');
        }
        for (CardType cardType : card.getCardType()) {
            sbType.append(cardType.toString()).append(' ');
        }
        if (!card.getSubtype(game).isEmpty()) {
            sbType.append("- ");
            for (SubType subType : card.getSubtype(game)) {
                sbType.append(subType).append(' ');
            }
        }
        return sbType.toString();
    }

    /**
     * @param card
     * @param game
     * @param controlled       is the card view created for the card controller - used
     *                         for morph / face down cards to know which player may see information for
     *                         the card
     * @param showFaceDownCard if true and the card is not on the battlefield,
     *                         also a face down card is shown in the view, face down cards will be shown
     * @param storeZone        if true the card zone will be set in the zone attribute.
     */
    public CardView(Card card, Game game, boolean controlled, boolean showFaceDownCard, boolean storeZone) {
        super(card.getId(), card.getExpansionSetCode(), card.getCardNumber(), card.getUsesVariousArt(), card.getTokenSetCode(), game != null, card.getTokenDescriptor());
        this.originalCard = card;

        // no information available for face down cards as long it's not a controlled face down morph card
        // TODO: Better handle this in Framework (but currently I'm not sure how to do it there) LevelX2
        boolean showFaceUp = true;
        if (game != null) {
            Zone cardZone = game.getState().getZone(card.getId());
            if (card.isFaceDown(game)) {
                showFaceUp = false;
                if (Zone.BATTLEFIELD != cardZone) {
                    if (showFaceDownCard) {
                        showFaceUp = true;
                    }
                }
            }

            if (storeZone) {
                this.zone = cardZone;
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
            } else if (card instanceof Permanent) {
                this.power = Integer.toString(card.getPower().getValue());
                this.toughness = Integer.toString(card.getToughness().getValue());
                this.cardTypes = card.getCardType();
                this.faceDown = card.isFaceDown(game);
            } else {
                // this.hideInfo = true;
                return;
            }
        }

        SplitCard splitCard = null;
        if (card.isSplitCard()) {
            splitCard = (SplitCard) card;
            rotate = (card.getSpellAbility().getSpellAbilityType()) != SpellAbilityType.SPLIT_AFTERMATH;
        } else if (card instanceof Spell) {
            switch (card.getSpellAbility().getSpellAbilityType()) {
                case SPLIT_FUSED:
                    splitCard = (SplitCard) ((Spell) card).getCard();
                    rotate = true;
                    break;
                case SPLIT_AFTERMATH:
                    splitCard = (SplitCard) ((Spell) card).getCard();
                    rotate = false;
                    break;
                case SPLIT_LEFT:
                case SPLIT_RIGHT:
                    rotate = true;
                    break;
            }
        }
        if (splitCard != null) {
            this.isSplitCard = true;
            leftSplitName = splitCard.getLeftHalfCard().getName();
            leftSplitCosts = splitCard.getLeftHalfCard().getManaCost();
            leftSplitRules = splitCard.getLeftHalfCard().getRules(game);
            leftSplitTypeLine = getCardTypeLine(game, splitCard.getLeftHalfCard());
            rightSplitName = splitCard.getRightHalfCard().getName();
            rightSplitCosts = splitCard.getRightHalfCard().getManaCost();
            rightSplitRules = splitCard.getRightHalfCard().getRules(game);
            rightSplitTypeLine = getCardTypeLine(game, splitCard.getRightHalfCard());
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
            if (game != null) {
                if (permanent.getCounters(game) != null && !permanent.getCounters(game).isEmpty()) {
                    this.loyalty = Integer.toString(permanent.getCounters(game).getCount(CounterType.LOYALTY));
                    counters = new ArrayList<>();
                    for (Counter counter : permanent.getCounters(game).values()) {
                        counters.add(new CounterView(counter));
                    }
                }
                this.pairedCard = permanent.getPairedCard() != null ? permanent.getPairedCard().getSourceId() : null;
                this.bandedCards = new ArrayList<>();
                for (UUID bandedCard : permanent.getBandedCards()) {
                    bandedCards.add(bandedCard);
                }
                if (!permanent.getControllerId().equals(permanent.getOwnerId())) {
                    controlledByOwner = false;
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
        this.subTypes = card.getSubtype(game);
        this.superTypes = card.getSuperType();
        this.color = card.getColor(game);
        this.transformable = card.isTransformable();
        this.flipCard = card.isFlipCard();
        this.faceDown = !showFaceUp;

        if (card instanceof PermanentToken) {
            this.isToken = true;
            this.mageObjectType = MageObjectType.TOKEN;
            this.rarity = Rarity.COMMON;
            boolean originalCardNumberIsNull = ((PermanentToken) card).getToken().getOriginalCardNumber() == null;
            if (!originalCardNumberIsNull && !"0".equals(((PermanentToken) card).getToken().getOriginalCardNumber())) {
                // a token copied from permanent
                this.expansionSetCode = ((PermanentToken) card).getToken().getOriginalExpansionSetCode();
                this.cardNumber = ((PermanentToken) card).getToken().getOriginalCardNumber();
            } else {
                // a created token
                this.expansionSetCode = card.getExpansionSetCode();
                this.tokenDescriptor = card.getTokenDescriptor();
            }
            //
            // set code und card number for token copies to get the image
            this.rules = card.getRules(game);
            this.type = ((PermanentToken) card).getToken().getTokenType();
        } else {
            this.rarity = card.getRarity();
            this.isToken = false;
        }

        Card secondSideCard = card.getSecondCardFace();
        if (secondSideCard != null) {
            this.secondCardFace = new CardView(secondSideCard);
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
                    Mode mode = spellAbility.getModes().get(modeId);
                    if (!mode.getTargets().isEmpty()) {
                        setTargets(mode.getTargets());
                    }
                }
            }

            // Determine what part of the art to slice out for spells on the stack which originate
            // from a split, fuse, or aftermath split card.
            SpellAbilityType ty = spell.getSpellAbility().getSpellAbilityType();
            if (ty == SpellAbilityType.SPLIT_RIGHT || ty == SpellAbilityType.SPLIT_LEFT || ty == SpellAbilityType.SPLIT_FUSED) {
                // Needs a special art rect
                if (ty == SpellAbilityType.SPLIT_FUSED) {
                    artRect = ArtRect.SPLIT_FUSED;
                } else if (spell.getCard() != null) {
                    SplitCard wholeCard = ((SplitCardHalf) spell.getCard()).getParentCard();
                    Abilities<Ability> aftermathHalfAbilities = wholeCard.getRightHalfCard().getAbilities();
                    if (aftermathHalfAbilities.stream().anyMatch(ability -> ability instanceof AftermathAbility)) {
                        if (ty == SpellAbilityType.SPLIT_RIGHT) {
                            artRect = ArtRect.AFTERMATH_BOTTOM;
                        } else {
                            artRect = ArtRect.AFTERMATH_TOP;
                        }
                    } else if (ty == SpellAbilityType.SPLIT_RIGHT) {
                        artRect = ArtRect.SPLIT_RIGHT;
                    } else {
                        artRect = ArtRect.SPLIT_LEFT;
                    }
                }
            }

            // show for modal spell, which mode was choosen
            if (spell.getSpellAbility().isModal()) {
                for (UUID modeId : spell.getSpellAbility().getModes().getSelectedModes()) {
                    Mode mode = spell.getSpellAbility().getModes().get(modeId);
                    this.rules.add("<span color='green'><i>Chosen mode: " + mode.getEffects().getText(mode) + "</i></span>");
                }
            }
        }

        // Frame color
        this.frameColor = card.getFrameColor(game);

        // Frame style
        this.frameStyle = card.getFrameStyle();

        // Get starting loyalty
        this.startingLoyalty = "" + card.getStartingLoyalty();


    }

    public CardView(MageObject object) {
        super(object.getId(), "", "0", false, "", true, "");
        this.originalCard = null;

        this.name = object.getName();
        this.displayName = object.getName();
        if (object instanceof Permanent) {
            this.mageObjectType = MageObjectType.PERMANENT;
            this.power = Integer.toString(object.getPower().getValue());
            this.toughness = Integer.toString(object.getToughness().getValue());
            this.loyalty = Integer.toString(((Permanent) object).getCounters((Game) null).getCount(CounterType.LOYALTY));
        } else {
            this.power = object.getPower().toString();
            this.toughness = object.getToughness().toString();
            this.loyalty = "";
        }
        this.cardTypes = object.getCardType();
        this.subTypes = object.getSubtype(null);
        this.superTypes = object.getSuperType();
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
        } else if (object instanceof Plane) {
            this.mageObjectType = MageObjectType.PLANE;
            Plane plane = (Plane) object;
            this.rarity = Rarity.SPECIAL;
            this.frameStyle = FrameStyle.M15_NORMAL;
            // Display in landscape/rotated/on its side
            this.rotate = true;
            this.rules = plane.getAbilities().getRules(plane.getName());
        } else if (object instanceof Designation) {
            this.mageObjectType = MageObjectType.DESIGNATION;
            Designation designation = (Designation) object;
            this.rarity = Rarity.SPECIAL;
            this.frameStyle = FrameStyle.M15_NORMAL;
            // Display in landscape/rotated/on its side
            this.rules = designation.getAbilities().getRules(designation.getName());
        }
        if (this.rarity == null && object instanceof StackAbility) {
            StackAbility stackAbility = (StackAbility) object;
            this.rarity = Rarity.SPECIAL;
            this.rules = new ArrayList<>();
            this.rules.add(stackAbility.getRule());
            if (stackAbility.getZone() == Zone.COMMAND) {
                this.expansionSetCode = stackAbility.getExpansionSetCode();
            }
        }
        // Frame color
        this.frameColor = object.getFrameColor(null);
        // Frame style
        this.frameStyle = object.getFrameStyle();
        // Starting loyalty. Must be extracted from an ability
        this.startingLoyalty = "" + object.getStartingLoyalty();
    }

    protected CardView() {
        super(null, "", "0", false, "", true, "");
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
        this.frameStyle = FrameStyle.M15_NORMAL;
        this.expansionSetCode = emblem.getExpansionSetCode();
        this.rarity = Rarity.COMMON;
    }

    public CardView(PlaneView plane) {
        this(true);
        this.gameObject = true;
        this.id = plane.getId();
        this.mageObjectType = MageObjectType.PLANE;
        this.name = plane.getName();
        this.displayName = name;
        this.rules = plane.getRules();
        // Display the plane in landscape (similar to Fused cards)
        this.rotate = true;
        this.frameStyle = FrameStyle.M15_NORMAL;
        this.expansionSetCode = plane.getExpansionSetCode();
        this.rarity = Rarity.COMMON;
    }

    public CardView(Designation designation, StackAbility stackAbility) {
        this(true);
        this.gameObject = true;
        this.id = designation.getId();
        this.mageObjectType = MageObjectType.NULL;
        this.name = designation.getName();
        this.displayName = name;
        this.rules = new ArrayList<>();
        this.rules.add(stackAbility.getRule(designation.getName()));
        this.frameStyle = FrameStyle.M15_NORMAL;
        this.expansionSetCode = designation.getExpansionSetCodeForImage();
        this.rarity = Rarity.COMMON;
    }

    public CardView(boolean empty) {
        super(null, "", "0", false, "", "");
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
        this.startingLoyalty = "";
        this.cardTypes = EnumSet.noneOf(CardType.class);
        this.subTypes = new SubTypeList();
        this.superTypes = EnumSet.noneOf(SuperType.class);
        this.color = new ObjectColor();
        this.frameColor = new ObjectColor();
        this.frameStyle = FrameStyle.M15_NORMAL;
        this.manaCost = new ArrayList<>();
        this.convertedManaCost = 0;

        // the controller can see more information (e.g. enlarged image) than other players for face down cards (e.g. Morph played face down)
        if (!controlled) {
            this.rarity = Rarity.COMMON;
            this.expansionSetCode = "";
            this.cardNumber = "0";
        } else {
            this.rarity = card.getRarity();
        }

        if (card != null) {
            if (card instanceof Permanent) {
                this.mageObjectType = MageObjectType.PERMANENT;
            } else if (card.isCopy()) {
                this.mageObjectType = MageObjectType.COPY_CARD;
            } else {
                this.mageObjectType = MageObjectType.CARD;
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
        super(token.getId(), "", "0", false, "", "");
        this.isToken = true;
        this.id = token.getId();
        this.name = token.getName();
        this.displayName = token.getName();
        this.rules = token.getAbilities().getRules(this.name);
        this.power = token.getPower().toString();
        this.toughness = token.getToughness().toString();
        this.loyalty = "";
        this.startingLoyalty = "";
        this.cardTypes = token.getCardType();
        this.subTypes = token.getSubtype(null);
        this.superTypes = token.getSuperType();
        this.color = token.getColor(null);
        this.frameColor = token.getFrameColor(null);
        this.frameStyle = token.getFrameStyle();
        this.manaCost = token.getManaCost().getSymbols();
        this.rarity = Rarity.SPECIAL;
        this.type = token.getTokenType();
        this.tokenDescriptor = token.getTokenDescriptor();
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

    public String getStartingLoyalty() {
        return startingLoyalty;
    }

    public Set<CardType> getCardTypes() {
        return cardTypes;
    }

    public SubTypeList getSubTypes() {
        return subTypes;
    }

    public Set<SuperType> getSuperTypes() {
        return superTypes;
    }

    public ObjectColor getColor() {
        return color;
    }

    public ObjectColor getFrameColor() {
        return frameColor;
    }

    public FrameStyle getFrameStyle() {
        return frameStyle;
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
        if (expansionSetCode == null) {
            expansionSetCode = "";
        }
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
    public String getCardNumber() {
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
        return getName() + " [" + getId() + ']';
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public boolean canTransform() {
        return this.transformable;
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

    public String getLeftSplitTypeLine() {
        return leftSplitTypeLine;
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

    public String getRightSplitTypeLine() {
        return rightSplitTypeLine;
    }

    public ArtRect getArtRect() {
        return artRect;
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

    public List<UUID> getBandedCards() {
        return bandedCards;
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

    public Zone getZone() {
        return zone;
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

    public boolean isCanBlock() {
        return canBlock;
    }

    public void setCanBlock(boolean canBlock) {
        this.canBlock = canBlock;
    }

    public boolean isCreature() {
        return cardTypes.contains(CardType.CREATURE);
    }

    public boolean isPlanesWalker() {
        return cardTypes.contains(CardType.PLANESWALKER);
    }

    public String getColorText() {

        String color = getColor().getDescription();
        return color.substring(0, 1).toUpperCase(Locale.ENGLISH) + color.substring(1);
    }

    public String getTypeText() {
        StringBuilder type = new StringBuilder();
        if (!getSuperTypes().isEmpty()) {
            type.append(String.join(" ", getSuperTypes().stream().map(SuperType::toString).collect(Collectors.toList())));
            type.append(" ");
        }
        if (!getCardTypes().isEmpty()) {
            type.append(String.join(" ", getCardTypes().stream().map(CardType::toString).collect(Collectors.toList())));
            type.append(" ");
        }
        if (!getSubTypes().isEmpty()) {
            type.append(" - ");
            type.append(String.join(" ", getSubTypes().stream().map(SubType::toString).collect(Collectors.toList())));
        }
        return type.toString();
    }

    public boolean isLand() {
        return cardTypes.contains(CardType.LAND);
    }

    public boolean isInstant() {
        return cardTypes.contains(CardType.INSTANT);
    }

    public boolean isSorcery() {
        return cardTypes.contains(CardType.SORCERY);
    }

    public boolean isEnchantment() {
        return cardTypes.contains(CardType.ENCHANTMENT);
    }

    public boolean isArtifact() {
        return cardTypes.contains(CardType.ARTIFACT);
    }

    public boolean isTribal() {
        return cardTypes.contains(CardType.TRIBAL);
    }

    public void setInViewerOnly(boolean inViewerOnly) {
        this.inViewerOnly = inViewerOnly;
    }

    public boolean inViewerOnly() {
        return inViewerOnly;
    }

    public Card getOriginalCard() {
        return this.originalCard;
    }
}
