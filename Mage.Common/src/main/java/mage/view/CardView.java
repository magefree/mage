package mage.view;

import com.google.gson.annotations.Expose;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.icon.CardIcon;
import mage.abilities.icon.other.CommanderCardIcon;
import mage.abilities.icon.other.FaceDownCardIcon;
import mage.abilities.icon.other.VariableCostCardIcon;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.*;
import mage.cards.mock.MockCard;
import mage.cards.repository.CardInfo;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.designations.Designation;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.command.Dungeon;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;
import mage.util.CardUtil;
import mage.util.SubTypes;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardView extends SimpleCardView {

    private static final long serialVersionUID = 1L;

    protected UUID parentId;
    @Expose
    protected String name;
    @Expose
    protected String displayName;
    @Expose
    protected String displayFullName;
    @Expose
    protected List<String> rules;
    @Expose
    protected String power;
    @Expose
    protected String toughness;
    @Expose
    protected String loyalty = "";
    protected String startingLoyalty;
    protected List<CardType> cardTypes;
    protected SubTypes subTypes;
    protected Set<SuperType> superTypes;
    protected ObjectColor color;
    protected ObjectColor frameColor;
    protected FrameStyle frameStyle;
    // can combine multiple costs for MockCard from deck editor or db (left/right, card/adventure)
    protected String manaCostLeftStr;
    protected String manaCostRightStr;
    protected int manaValue;
    protected Rarity rarity;

    protected MageObjectType mageObjectType = MageObjectType.NULL;

    protected boolean isAbility;
    protected AbilityType abilityType;
    protected boolean isToken;

    protected CardView ability;
    protected int type;

    protected boolean transformable; // can toggle one card side to another (transformable cards, modal double faces)
    protected CardView secondCardFace;
    protected boolean transformed;

    protected boolean flipCard;
    protected boolean faceDown;

    protected String alternateName;

    protected boolean isSplitCard;
    protected String leftSplitName;
    protected String leftSplitCostsStr;
    protected List<String> leftSplitRules;
    protected String leftSplitTypeLine;
    protected String rightSplitName;
    protected String rightSplitCostsStr;
    protected List<String> rightSplitRules;
    protected String rightSplitTypeLine;

    protected boolean isModalDoubleFacesCard;

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

    protected boolean canAttack;
    protected boolean canBlock;
    protected boolean inViewerOnly;
    protected List<CardIcon> cardIcons = new ArrayList<>(); // additional icons to render

    protected Card originalCard = null;

    /**
     * Non game usage like deck editor
     *
     * @param card
     */
    public CardView(Card card) {
        this(card, (Game) null);
    }

    public CardView(Card card, Game game) {
        this(card, game, false);
    }

    public CardView(Card card, SimpleCardView simpleCardView) {
        this(card, null, false);
        this.id = simpleCardView.getId();

        this.playableStats = simpleCardView.playableStats.copy();
        this.isChoosable = simpleCardView.isChoosable;
        this.isSelected = simpleCardView.isSelected;
    }

    public CardView(Card card, Game game, UUID cardId) {
        this(card, game, false);
        this.id = cardId;
    }

    public CardView(final CardView cardView) {
        super(cardView);

        // generetate new ID (TODO: why new ID?)
        this.id = UUID.randomUUID();
        this.parentId = cardView.parentId;

        this.name = cardView.name;
        this.displayName = cardView.displayName;
        this.displayFullName = cardView.displayFullName;
        this.rules = new ArrayList<>(cardView.rules);

        this.power = cardView.power;
        this.toughness = cardView.toughness;
        this.loyalty = cardView.loyalty;
        this.startingLoyalty = cardView.startingLoyalty;
        this.cardTypes = new ArrayList<>(cardView.cardTypes);
        this.subTypes = new SubTypes(cardView.subTypes);
        this.superTypes = cardView.superTypes;

        this.color = cardView.color.copy();
        this.frameColor = cardView.frameColor.copy();
        this.frameStyle = cardView.frameStyle;
        this.manaCostLeftStr = cardView.manaCostLeftStr;
        this.manaCostRightStr = cardView.manaCostRightStr;
        this.manaValue = cardView.manaValue;
        this.rarity = cardView.rarity;

        this.mageObjectType = cardView.mageObjectType;
        this.isAbility = cardView.isAbility;
        this.abilityType = cardView.abilityType;
        this.isToken = cardView.isToken;
        this.ability = cardView.ability; // reference, not copy
        this.type = cardView.type;

        this.transformable = cardView.transformable;
        this.secondCardFace = cardView.secondCardFace == null ? null : new CardView(cardView.secondCardFace);
        this.transformed = cardView.transformed;
        this.flipCard = cardView.flipCard;
        this.faceDown = cardView.faceDown;
        this.alternateName = cardView.alternateName;

        this.isSplitCard = cardView.isSplitCard;
        this.leftSplitName = cardView.leftSplitName;
        this.leftSplitCostsStr = cardView.leftSplitCostsStr;
        this.leftSplitRules = cardView.leftSplitRules == null ? null : new ArrayList<>(cardView.leftSplitRules);
        this.leftSplitTypeLine = cardView.leftSplitTypeLine;
        this.rightSplitName = cardView.rightSplitName;
        this.rightSplitCostsStr = cardView.rightSplitCostsStr;
        this.rightSplitRules = cardView.rightSplitRules == null ? null : new ArrayList<>(cardView.rightSplitRules);
        this.rightSplitTypeLine = cardView.rightSplitTypeLine;

        this.isModalDoubleFacesCard = cardView.isModalDoubleFacesCard;

        this.artRect = cardView.artRect;
        this.targets = cardView.targets == null ? null : new ArrayList<>(cardView.targets);
        this.pairedCard = cardView.pairedCard;
        this.bandedCards = cardView.bandedCards == null ? null : new ArrayList<>(cardView.bandedCards);
        this.paid = cardView.paid;
        if (cardView.counters != null) {
            this.counters = new ArrayList<>();
            cardView.counters.forEach(c -> this.counters.add(new CounterView(c)));
        }

        this.controlledByOwner = cardView.controlledByOwner;
        this.zone = cardView.zone;
        this.rotate = cardView.rotate;
        this.hideInfo = cardView.hideInfo;

        this.canAttack = cardView.canAttack;
        this.canBlock = cardView.canBlock;
        this.inViewerOnly = cardView.inViewerOnly;
        this.originalCard = cardView.originalCard == null ? null : cardView.originalCard.copy();
        if (cardView.cardIcons != null) {
            cardView.cardIcons.forEach(icon -> this.cardIcons.add(icon.copy()));
        }
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
        for (CardType cardType : card.getCardType(game)) {
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
                    this.displayFullName = card.getName();
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
                this.cardTypes = new ArrayList<>(card.getCardType(game));
                this.faceDown = card.isFaceDown(game);
            } else {
                // this.hideInfo = true;
                return;
            }
        }

        SplitCard splitCard = null;
        if (card instanceof SplitCard) {
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
                case MODAL_LEFT:
                case MODAL_RIGHT:
                    rotate = false;
                    break;
            }
        }

        String fullCardName;
        if (splitCard != null) {
            this.isSplitCard = true;
            leftSplitName = splitCard.getLeftHalfCard().getName();
            leftSplitCostsStr = String.join("", splitCard.getLeftHalfCard().getManaCostSymbols());
            leftSplitRules = splitCard.getLeftHalfCard().getRules(game);
            leftSplitTypeLine = getCardTypeLine(game, splitCard.getLeftHalfCard());
            rightSplitName = splitCard.getRightHalfCard().getName();
            rightSplitCostsStr = String.join("", splitCard.getRightHalfCard().getManaCostSymbols());
            rightSplitRules = splitCard.getRightHalfCard().getRules(game);
            rightSplitTypeLine = getCardTypeLine(game, splitCard.getRightHalfCard());

            fullCardName = card.getName(); // split card contains full name as normal
            this.manaCostLeftStr = String.join("", splitCard.getLeftHalfCard().getManaCostSymbols());
            this.manaCostRightStr = String.join("", splitCard.getRightHalfCard().getManaCostSymbols());
        } else if (card instanceof ModalDoubleFacesCard) {
            this.isModalDoubleFacesCard = true;
            ModalDoubleFacesCard mainCard = ((ModalDoubleFacesCard) card);
            fullCardName = mainCard.getLeftHalfCard().getName() + MockCard.MODAL_DOUBLE_FACES_NAME_SEPARATOR + mainCard.getRightHalfCard().getName();
            this.manaCostLeftStr = String.join("", mainCard.getLeftHalfCard().getManaCostSymbols());
            this.manaCostRightStr = String.join("", mainCard.getRightHalfCard().getManaCostSymbols());
        } else if (card instanceof AdventureCard) {
            AdventureCard adventureCard = ((AdventureCard) card);
            AdventureCardSpell adventureCardSpell = adventureCard.getSpellCard();
            fullCardName = adventureCard.getName() + MockCard.ADVENTURE_NAME_SEPARATOR + adventureCardSpell.getName();
            this.manaCostLeftStr = String.join("", adventureCardSpell.getManaCostSymbols());
            this.manaCostRightStr = String.join("", adventureCard.getManaCostSymbols());
        } else if (card instanceof MockCard) {
            // deck editor cards
            fullCardName = ((MockCard) card).getFullName(true);
            this.manaCostLeftStr = String.join("", ((MockCard) card).getManaCostStr(CardInfo.ManaCostSide.LEFT));
            this.manaCostRightStr = String.join("", ((MockCard) card).getManaCostStr(CardInfo.ManaCostSide.RIGHT));
        } else {
            fullCardName = card.getName();
            this.manaCostLeftStr = String.join("", card.getManaCostSymbols());
            this.manaCostRightStr = "";
        }

        this.name = card.getImageName();
        this.displayName = card.getName();
        this.displayFullName = fullCardName;
        if (game == null) {
            this.rules = new ArrayList<>(card.getRules());
        } else {
            this.rules = new ArrayList<>(card.getRules(game));
        }
        this.manaValue = card.getManaValue();

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

            // card icons for permanents on battlefield
            // abilities
            permanent.getAbilities(game).forEach(ability -> {
                this.cardIcons.addAll(ability.getIcons(game));
            });
            // face down
            if (permanent.isFaceDown(game)) {
                this.cardIcons.add(FaceDownCardIcon.instance);
            }
            // commander
            if (game != null) {
                Player owner = game.getPlayer(game.getOwnerId(permanent));
                if (owner != null && game.isCommanderObject(owner, permanent)) {
                    this.cardIcons.add(CommanderCardIcon.instance);
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

        // card icons for any permanents and cards
        if (game != null) {
            // x cost
            Zone cardZone = game.getState().getZone(card.getId());
            if (card.getManaCost().containsX()
                    && card.getSpellAbility() != null
                    && (cardZone.match(Zone.BATTLEFIELD) || cardZone.match(Zone.STACK))) {
                int costX;
                if (card instanceof Permanent) {
                    // permanent on battlefield (can show x icon multiple turns, so use end_game source)
                    costX = ManacostVariableValue.END_GAME.calculate(game, card.getSpellAbility(), null);
                } else {
                    // other like Stack (can show x icon on stack only, so use normal source)
                    costX = ManacostVariableValue.REGULAR.calculate(game, card.getSpellAbility(), null);
                }
                this.cardIcons.add(new VariableCostCardIcon(costX));
            }
        }

        this.power = Integer.toString(card.getPower().getValue());
        this.toughness = Integer.toString(card.getToughness().getValue());
        this.cardTypes = new ArrayList<>(card.getCardType(game));
        this.subTypes = new SubTypes(card.getSubtype(game));
        this.superTypes = card.getSuperType();
        this.color = card.getColor(game).copy();
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
            // set code and card number for token copies to get the image
            this.rules = new ArrayList<>(card.getRules(game));
            this.type = ((PermanentToken) card).getToken().getTokenType();
        } else {
            this.rarity = card.getRarity();
            this.isToken = false;
        }

        // transformable, double faces cards
        this.transformable = card.isTransformable();

        Card secondSideCard = card.getSecondCardFace();
        if (secondSideCard != null) {
            this.secondCardFace = new CardView(secondSideCard, game);
            this.alternateName = secondCardFace.getName();
        }

        this.flipCard = card.isFlipCard();
        if (card.isFlipCard() && card.getFlipCardName() != null) {
            this.alternateName = card.getFlipCardName();
        }

        if (card instanceof ModalDoubleFacesCard) {
            this.transformable = true; // enable GUI day/night button
            ModalDoubleFacesCard mdfCard = (ModalDoubleFacesCard) card;
            this.secondCardFace = new CardView(mdfCard.getRightHalfCard(), game);
            this.alternateName = mdfCard.getRightHalfCard().getName();
        }

        if (card instanceof Spell) {
            this.mageObjectType = MageObjectType.SPELL;
            Spell spell = (Spell) card;
            for (SpellAbility spellAbility : spell.getSpellAbilities()) {
                for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
                    Mode mode = spellAbility.getModes().get(modeId);
                    if (!mode.getTargets().isEmpty()) {
                        addTargets(mode.getTargets(), mode.getEffects(), spellAbility, game);
                    }
                }
            }

            // Determine what part of the art to slice out for spells on the stack which originate
            // from a split, fuse, or aftermath split card.
            // Modal double faces cards draws as normal cards
            SpellAbilityType ty = spell.getSpellAbility().getSpellAbilityType();
            if (ty == SpellAbilityType.SPLIT_RIGHT || ty == SpellAbilityType.SPLIT_LEFT || ty == SpellAbilityType.SPLIT_FUSED) {
                // Needs a special art rect
                if (ty == SpellAbilityType.SPLIT_FUSED) {
                    artRect = ArtRect.SPLIT_FUSED;
                } else if (spell.getCard() != null) {
                    SplitCard wholeCard = ((SplitCardHalf) spell.getCard()).getParentCard();
                    Abilities<Ability> aftermathHalfAbilities = wholeCard.getRightHalfCard().getAbilities(game);
                    if (aftermathHalfAbilities.stream().anyMatch(AftermathAbility.class::isInstance)) {
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

            // show for modal spell, which mode was chosen
            if (spell.getSpellAbility().isModal()) {
                for (UUID modeId : spell.getSpellAbility().getModes().getSelectedModes()) {
                    Mode mode = spell.getSpellAbility().getModes().get(modeId);
                    this.rules.add("<span color='green'><i>Chosen mode: " + mode.getEffects().getText(mode) + "</i></span>");
                }
            }

            // show target of a spell on the stack
            if (!spell.getSpellAbility().getTargets().isEmpty()) {
                StackObject stackObjectTarget = null;
                for (Target target : spell.getSpellAbility().getTargets()) {
                    for (UUID targetId : target.getTargets()) {
                        MageObject mo = game.getObject(targetId);
                        if (mo instanceof StackObject) {
                            stackObjectTarget = (StackObject) mo;
                        }
                        if (stackObjectTarget != null) {
                            this.rules.add("<span color='green'><i>Target on stack: " + stackObjectTarget.getIdName());
                        }
                    }
                }

            }
        }

        // Frame color
        this.frameColor = card.getFrameColor(game).copy();

        // Frame style
        this.frameStyle = card.getFrameStyle();

        // Get starting loyalty
        this.startingLoyalty = CardUtil.convertStartingLoyalty(card.getStartingLoyalty());
    }

    public CardView(MageObject object, Game game) {
        super(object.getId(), "", "0", false, "", true, "");
        this.originalCard = null;

        this.name = object.getName();
        this.displayName = object.getName();
        this.displayFullName = object.getName();
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
        this.cardTypes = new ArrayList<>(object.getCardType(game));
        this.subTypes = new SubTypes(object.getSubtype(game));
        this.superTypes = object.getSuperType();
        this.color = object.getColor(game).copy();
        this.manaCostLeftStr = String.join("", object.getManaCostSymbols());
        this.manaCostRightStr = "";
        this.manaValue = object.getManaCost().manaValue();
        if (object instanceof PermanentToken) {
            this.mageObjectType = MageObjectType.TOKEN;
            PermanentToken permanentToken = (PermanentToken) object;
            this.rarity = Rarity.COMMON;
            this.expansionSetCode = permanentToken.getExpansionSetCode();
            this.rules = new ArrayList<>(permanentToken.getRules());
            this.type = permanentToken.getToken().getTokenType();
        } else if (object instanceof Emblem) {
            this.mageObjectType = MageObjectType.EMBLEM;
            Emblem emblem = (Emblem) object;
            this.rarity = Rarity.SPECIAL;
            this.rules = new ArrayList<>(emblem.getAbilities().getRules(emblem.getName()));
        } else if (object instanceof Dungeon) {
            this.mageObjectType = MageObjectType.DUNGEON;
            Dungeon dungeon = (Dungeon) object;
            this.rarity = Rarity.SPECIAL;
            this.rules = new ArrayList<>(dungeon.getRules());
        } else if (object instanceof Plane) {
            this.mageObjectType = MageObjectType.PLANE;
            Plane plane = (Plane) object;
            this.rarity = Rarity.SPECIAL;
            this.frameStyle = FrameStyle.M15_NORMAL;
            // Display in landscape/rotated/on its side
            this.rotate = true;
            this.rules = new ArrayList<>(plane.getAbilities().getRules(plane.getName()));
        } else if (object instanceof Designation) {
            this.mageObjectType = MageObjectType.DESIGNATION;
            Designation designation = (Designation) object;
            this.rarity = Rarity.SPECIAL;
            this.frameStyle = FrameStyle.M15_NORMAL;
            // Display in landscape/rotated/on its side
            this.rules = new ArrayList<>(designation.getAbilities().getRules(designation.getName()));
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
        this.frameColor = object.getFrameColor(game).copy();
        // Frame style
        this.frameStyle = object.getFrameStyle();
        // Starting loyalty. Must be extracted from an ability
        this.startingLoyalty = CardUtil.convertStartingLoyalty(object.getStartingLoyalty());
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
        this.displayFullName = name;
        this.rules = new ArrayList<>(emblem.getRules());
        // emblem images are always with common (black) symbol
        this.frameStyle = FrameStyle.M15_NORMAL;
        this.expansionSetCode = emblem.getExpansionSetCode();
        this.rarity = Rarity.COMMON;
    }

    public CardView(DungeonView dungeon) {
        this(true);
        this.gameObject = true;
        this.id = dungeon.getId();
        this.mageObjectType = MageObjectType.DUNGEON;
        this.name = dungeon.getName();
        this.displayName = name;
        this.displayFullName = name;
        this.rules = new ArrayList<>(dungeon.getRules());
        // emblem images are always with common (black) symbol
        this.frameStyle = FrameStyle.M15_NORMAL;
        this.expansionSetCode = dungeon.getExpansionSetCode();
        this.rarity = Rarity.COMMON;
    }

    public CardView(PlaneView plane) {
        this(true);
        this.gameObject = true;
        this.id = plane.getId();
        this.mageObjectType = MageObjectType.PLANE;
        this.name = plane.getName();
        this.displayName = name;
        this.displayFullName = name;
        this.rules = new ArrayList<>(plane.getRules());
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
        this.displayFullName = name;
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
        this.displayFullName = name;
        this.rules = new ArrayList<>();
        this.power = "";
        this.toughness = "";
        this.loyalty = "";
        this.startingLoyalty = "";
        this.cardTypes = new ArrayList<>();
        this.subTypes = new SubTypes();
        this.superTypes = EnumSet.noneOf(SuperType.class);
        this.color = new ObjectColor();
        this.frameColor = new ObjectColor();
        this.frameStyle = FrameStyle.M15_NORMAL;
        this.manaCostLeftStr = "";
        this.manaCostRightStr = "";
        this.manaValue = 0;

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

    CardView(Token token, Game game) {
        super(token.getId(), "", "0", false, "", "");
        this.isToken = true;
        this.id = token.getId();
        this.name = token.getName();
        this.displayName = token.getName();
        this.displayFullName = token.getName();
        this.rules = new ArrayList<>(token.getAbilities().getRules(this.name));
        this.power = token.getPower().toString();
        this.toughness = token.getToughness().toString();
        this.loyalty = "";
        this.startingLoyalty = "";
        this.cardTypes = new ArrayList<>(token.getCardType(game));
        this.subTypes = new SubTypes(token.getSubtype(game));
        this.superTypes = token.getSuperType();
        this.color = token.getColor(game).copy();
        this.frameColor = token.getFrameColor(game).copy();
        this.frameStyle = token.getFrameStyle();
        this.manaCostLeftStr = String.join("", token.getManaCostSymbols());
        this.manaCostRightStr = "";
        this.rarity = Rarity.SPECIAL;
        this.type = token.getTokenType();
        this.tokenDescriptor = token.getTokenDescriptor();
        this.tokenSetCode = token.getOriginalExpansionSetCode();
    }

    protected final void addTargets(Targets targets, Effects effects, Ability source, Game game) {
        if (this.targets == null) {
            this.targets = new ArrayList<>();
        }

        // need only unique targets for arrow drawning
        Set<UUID> newTargets = new HashSet<>();

        // from normal targets
        for (Target target : targets) {
            if (target.isChosen()) {
                newTargets.addAll(target.getTargets());
            }
        }

        // from targetPointers (can be same as normal targets)
        List<UUID> fromPointers = effects.stream()
                .map(Effect::getTargetPointer)
                .filter(Objects::nonNull)
                .map(p -> p.getTargets(game, source))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        newTargets.addAll(fromPointers);

        this.targets.addAll(newTargets);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayFullName() {
        return displayFullName;
    }

    public List<String> getRules() {
        return rules;
    }

    public void overrideRules(List<String> rules) {
        this.rules = new ArrayList<>(rules);
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

    public List<CardType> getCardTypes() {
        return cardTypes;
    }

    public SubTypes getSubTypes() {
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

    public String getManaCostStr() {
        return CardUtil.concatManaSymbols(CardInfo.SPLIT_MANA_SEPARATOR_FULL, this.manaCostLeftStr, this.manaCostRightStr);
    }

    public int getManaValue() {
        return manaValue;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getColorIdentityStr() {
        FilterMana filterMana = originalCard.getColorIdentity();
        if (filterMana.getColorCount() == 0) {
            return CardUtil.concatManaSymbols(CardInfo.SPLIT_MANA_SEPARATOR_FULL, "{C}", "");
        }
        return CardUtil.concatManaSymbols(CardInfo.SPLIT_MANA_SEPARATOR_FULL, filterMana.toString(), "");
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
     * Name of the other side (transform), flipped, modal double faces card or
     * copying card name.
     *
     * @return name
     */
    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public String getLeftSplitName() {
        return leftSplitName;
    }

    public String getLeftSplitCostsStr() {
        return leftSplitCostsStr;
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

    public String getRightSplitCostsStr() {
        return rightSplitCostsStr;
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
        String colorText = getColor().getDescription();
        return colorText.substring(0, 1).toUpperCase(Locale.ENGLISH) + colorText.substring(1);
    }

    public String getTypeText() {
        StringBuilder typeText = new StringBuilder();
        if (!getSuperTypes().isEmpty()) {
            typeText.append(String.join(" ", getSuperTypes().stream().map(SuperType::toString).collect(Collectors.toList())));
            typeText.append(" ");
        }
        if (!getCardTypes().isEmpty()) {
            typeText.append(String.join(" ", getCardTypes().stream().map(CardType::toString).collect(Collectors.toList())));
            typeText.append(" ");
        }
        if (!getSubTypes().isEmpty()) {
            typeText.append(" - ");
            typeText.append(String.join(" ", getSubTypes().stream().map(SubType::toString).collect(Collectors.toList())));
        }
        return typeText.toString();
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

    public List<CardIcon> getCardIcons() {
        return this.cardIcons;
    }
}
