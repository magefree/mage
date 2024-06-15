package mage.view;

import com.google.gson.annotations.Expose;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.hint.HintUtils;
import mage.abilities.icon.CardIcon;
import mage.abilities.icon.CardIconImpl;
import mage.abilities.icon.CardIconType;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.*;
import mage.cards.mock.MockCard;
import mage.cards.repository.CardInfo;
import mage.cards.repository.TokenInfo;
import mage.cards.repository.TokenRepository;
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
import mage.util.ManaUtil;
import mage.util.SubTypes;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class CardView extends SimpleCardView {

    private static final long serialVersionUID = 1L;

    protected UUID parentId;
    @Expose
    protected String name; // TODO: remove duplicated field name/displayName???
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
    @Expose
    protected String defense = "";
    protected String startingLoyalty;
    protected String startingDefense;
    protected List<CardType> cardTypes;
    protected SubTypes subTypes;
    protected List<SuperType> superTypes;
    protected ObjectColor color;
    protected ObjectColor frameColor;
    protected FrameStyle frameStyle;
    // can combine multiple costs for MockCard from deck editor or db (left/right, card/adventure)
    protected List<String> manaCostLeftStr;
    protected List<String> manaCostRightStr;
    protected int manaValue;
    protected Rarity rarity;

    protected MageObjectType mageObjectType = MageObjectType.NULL;

    protected boolean isAbility;
    protected AbilityType abilityType;
    protected boolean isToken;

    protected CardView ability;
    protected String imageFileName = "";
    protected int imageNumber = 0;

    protected boolean extraDeckCard;
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

    protected boolean isModalDoubleFacedCard;

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
    protected boolean inViewerOnly; // GUI render: show object as a card instead permanent (without PT, etc)
    protected List<CardIcon> cardIcons = new ArrayList<>(); // additional icons to render

    // GUI related: additional info about current object (example: real PT)
    // warning, do not send full object, use some fields only (client must not get any server side data)
    // warning, don't forget to hide it in face down cards (null)
    protected MageInt originalPower = null;
    protected MageInt originalToughness = null;
    protected String originalColorIdentity = null; // GUI related info for sorting, searching, etc
    protected boolean originalIsCopy = false;

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

    /**
     * @param card
     * @param game
     * @param showAsControlled is the card view created for the card controller - used
     *                         for morph / face down cards to know which player may see information for
     *                         the card TODO: turn controller can be here too?
     */
    public CardView(Card card, Game game, boolean showAsControlled) {
        this(card, game, showAsControlled, false);
    }

    public CardView(Card card, SimpleCardView simpleCardView) {
        this(card, null, false);
        this.id = simpleCardView.getId();

        this.playableStats = simpleCardView.playableStats.copy();
        this.isChoosable = simpleCardView.isChoosable;
        this.isSelected = simpleCardView.isSelected;
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
        this.defense = cardView.defense;
        this.startingDefense = cardView.startingDefense;
        this.cardTypes = new ArrayList<>(cardView.cardTypes);
        this.subTypes = cardView.subTypes.copy();
        this.superTypes = cardView.superTypes;

        this.expansionSetCode = cardView.expansionSetCode;
        this.cardNumber = cardView.cardNumber;
        this.imageFileName = cardView.imageFileName;
        this.imageNumber = cardView.imageNumber;

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

        this.extraDeckCard = cardView.extraDeckCard;
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

        this.isModalDoubleFacedCard = cardView.isModalDoubleFacedCard;

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

        if (cardView.cardIcons != null) {
            cardView.cardIcons.forEach(icon -> this.cardIcons.add(icon.copy()));
        }

        this.originalPower = cardView.originalPower;
        this.originalToughness = cardView.originalToughness;
        this.originalColorIdentity = cardView.originalColorIdentity;
        this.originalIsCopy = cardView.originalIsCopy;

        this.playableStats = cardView.playableStats.copy();
        this.isChoosable = cardView.isChoosable;
        this.isSelected = cardView.isSelected;
    }

    private static String getCardTypeLine(Game game, Card card) {
        StringBuilder sbType = new StringBuilder();
        for (SuperType superType : card.getSuperType(game)) {
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
     * @param sourceCard
     * @param game
     * @param showAsControlled is the card view created for the card controller/owner - used
     *                         for morph / face down cards to know which player may see information for
     *                         the card
     * @param storeZone        if true the card zone will be set in the zone attribute.
     */
    public CardView(Card sourceCard, Game game, boolean showAsControlled, boolean storeZone) {
        super(sourceCard.getId(), sourceCard.getExpansionSetCode(), sourceCard.getCardNumber(), sourceCard.getUsesVariousArt(), game != null);

        // TODO: it's too big and can be buggy (something miss?) - must check and refactor: setup face down/up params, setup shared data like counters and targets


        // Visible logic:
        //   * Normal card:
        //     - original name, original image
        //   * Face down card:
        //     * my cards or game end:
        //       - face down status + original name, face down image, day/night button
        //     * opponent cards:
        //       - face down status, face down image

        // find real name from original card, cause face down status can be applied to card/spell
        String sourceName = sourceCard.getMainCard().getName();

        // find real spell characteristics before resolve
        Card card = sourceCard.copy();
        if (game != null && card instanceof Spell) {
            card = ((Spell) card).getSpellAbility().getCharacteristics(game);
        }

        // use isFaceDown(game) only here to find real status, all other code must use this.faceDown
        this.faceDown = game != null && sourceCard.isFaceDown(game);
        boolean showFaceUp = !this.faceDown;

        // show real name and day/night button for controller or any player at the game's end
        boolean showHiddenFaceDownData = showAsControlled || (game != null && game.hasEnded());

        // default image info
        this.expansionSetCode = card.getExpansionSetCode();
        this.cardNumber = card.getCardNumber();
        this.imageFileName = card.getImageFileName();
        this.imageNumber = card.getImageNumber();
        this.usesVariousArt = card.getUsesVariousArt();

        // permanent data
        if (showFaceUp) {
            this.setOriginalValues(card);
        }

        if (game != null) {
            Zone cardZone = game.getState().getZone(card.getId());
            if (storeZone) {
                // TODO: research, why it used here?
                this.zone = cardZone;
            }
        }

        // FACE DOWN
        if (!showFaceUp) {
            this.fillEmptyWithImageInfo(game, card, true);

            // can show face up card name for controller or game end
            // TODO: add exception on non empty name of the faced-down card here
            String visibleName = CardUtil.getCardNameForGUI(showHiddenFaceDownData ? sourceName : "", this.imageFileName);
            this.name = visibleName;
            this.displayName = visibleName;
            this.displayFullName = visibleName;
            this.alternateName = visibleName;

            // TODO: remove workaround - all actual characteristics must get from a card -- same as normal card do
            // TODO: must use same code in all zones
            // workaround to add PT, creature type and face up ability text (for stack and battlefield zones only)
            // in other zones it has only face down status/name
            if (sourceCard instanceof Spell
                    || card instanceof Permanent) {
                this.power = Integer.toString(card.getPower().getValue());
                this.toughness = Integer.toString(card.getToughness().getValue());
                this.cardTypes = new ArrayList<>(card.getCardType());
                this.color = card.getColor(null).copy();
                this.superTypes = new ArrayList<>(card.getSuperType());
                this.subTypes = card.getSubtype().copy();
                this.rules = new ArrayList<>(card.getRules());
            }

            // GUI: enable day/night button to view original face up card
            if (showHiddenFaceDownData) {
                this.transformable = true;
                this.secondCardFace = new CardView(sourceCard.getMainCard()); // do not use game param, so it will take default card
                this.alternateName = sourceCard.getMainCard().getName();
            }
        }

        // FACE UP and shared data like counters

        if (showFaceUp) {
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
                this.manaCostLeftStr = splitCard.getLeftHalfCard().getManaCostSymbols();
                this.manaCostRightStr = splitCard.getRightHalfCard().getManaCostSymbols();
            } else if (card instanceof ModalDoubleFacedCard) {
                this.isModalDoubleFacedCard = true;
                ModalDoubleFacedCard mainCard = ((ModalDoubleFacedCard) card);
                fullCardName = mainCard.getLeftHalfCard().getName() + MockCard.MODAL_DOUBLE_FACES_NAME_SEPARATOR + mainCard.getRightHalfCard().getName();
                this.manaCostLeftStr = mainCard.getLeftHalfCard().getManaCostSymbols();
                this.manaCostRightStr = mainCard.getRightHalfCard().getManaCostSymbols();
            } else if (card instanceof AdventureCard) {
                this.isSplitCard = true;
                AdventureCard adventureCard = ((AdventureCard) card);
                leftSplitName = adventureCard.getName();
                leftSplitCostsStr = String.join("", adventureCard.getManaCostSymbols());
                leftSplitRules = adventureCard.getSharedRules(game);
                leftSplitTypeLine = getCardTypeLine(game, adventureCard);
                AdventureCardSpell adventureCardSpell = adventureCard.getSpellCard();
                rightSplitName = adventureCardSpell.getName();
                rightSplitCostsStr = String.join("", adventureCardSpell.getManaCostSymbols());
                rightSplitRules = adventureCardSpell.getRules(game);
                rightSplitTypeLine = getCardTypeLine(game, adventureCardSpell);
                fullCardName = adventureCard.getName() + MockCard.ADVENTURE_NAME_SEPARATOR + adventureCardSpell.getName();
                this.manaCostLeftStr = adventureCard.getManaCostSymbols();
                this.manaCostRightStr = adventureCardSpell.getManaCostSymbols();
            } else if (card instanceof MockCard) {
                // deck editor cards
                fullCardName = ((MockCard) card).getFullName(true);
                this.manaCostLeftStr = ((MockCard) card).getManaCostStr(CardInfo.ManaCostSide.LEFT);
                this.manaCostRightStr = ((MockCard) card).getManaCostStr(CardInfo.ManaCostSide.RIGHT);
            } else {
                fullCardName = card.getName();
                this.manaCostLeftStr = card.getManaCostSymbols();
                this.manaCostRightStr = new ArrayList<>();
            }

            this.name = card.getName();
            this.displayName = card.getName();
            this.displayFullName = fullCardName;
            this.rules = new ArrayList<>(card.getRules(game));
            this.manaValue = card.getManaValue();
        }

        // shared info - counters and other
        if (card instanceof Permanent) {
            this.mageObjectType = MageObjectType.PERMANENT;
            Permanent permanent = (Permanent) card;
            if (game != null) {
                if (permanent.getCounters(game) != null && !permanent.getCounters(game).isEmpty()) {
                    this.loyalty = Integer.toString(permanent.getCounters(game).getCount(CounterType.LOYALTY));
                    this.defense = Integer.toString(permanent.getCounters(game).getCount(CounterType.DEFENSE));
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
                if (permanent.isTransformed()) {
                    transformed = true;
                }
            }
        } else {
            if (card.isCopy()) {
                this.mageObjectType = MageObjectType.COPY_CARD;
            } else {
                this.mageObjectType = MageObjectType.CARD;
            }
            this.loyalty = "";
            this.defense = "";
            if (game != null && card.getCounters(game) != null && !card.getCounters(game).isEmpty()) {
                counters = new ArrayList<>();
                for (Counter counter : card.getCounters(game).values()) {
                    counters.add(new CounterView(counter));
                }
            }
        }

        // FACE UP INFO
        if (showFaceUp) {
            // TODO: extract characteristics setup to shared code (same for face down and normal cards)
            //  PT, card types/subtypes/super/color/rules
            this.power = Integer.toString(card.getPower().getValue());
            this.toughness = Integer.toString(card.getToughness().getValue());
            this.cardTypes = new ArrayList<>(card.getCardType(game));
            this.subTypes = card.getSubtype(game).copy();
            this.superTypes = card.getSuperType(game);
            this.color = card.getColor(game).copy();
            this.flipCard = card.isFlipCard();

            if (card instanceof PermanentToken) {
                this.isToken = true;
                this.mageObjectType = MageObjectType.TOKEN;
                this.rarity = Rarity.SPECIAL;
                this.rules = new ArrayList<>(card.getRules(game));
            } else {
                this.rarity = card.getRarity();
                this.isToken = false;
            }

            this.extraDeckCard = card.isExtraDeckCard();

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

            if (card instanceof ModalDoubleFacedCard) {
                this.transformable = true; // enable GUI day/night button
                ModalDoubleFacedCard mdfCard = (ModalDoubleFacedCard) card;
                this.secondCardFace = new CardView(mdfCard.getRightHalfCard(), game);
                this.alternateName = mdfCard.getRightHalfCard().getName();
            }

            Card meldsToCard = card.getMeldsToCard();
            if (meldsToCard != null) {
                this.transformable = true; // enable GUI day/night button
                this.secondCardFace = new CardView(meldsToCard, game);
                this.alternateName = meldsToCard.getName();
            }

            if (card instanceof PermanentToken && card.isTransformable()) {
                Token backFace = (Token) ((PermanentToken) card).getOtherFace();
                this.secondCardFace = new CardView(backFace, game);
                this.alternateName = backFace.getName();
            }
        }

        // shared info - targets
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

        // render info
        if (showFaceUp) {
            if (card instanceof Spell) {
                Spell spell = (Spell) card;
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
            }

            // Cases, classes and sagas have portrait art
            if (card.getSubtype(game).contains(SubType.CASE) ||
                    card.getSubtype(game).contains(SubType.CLASS)) {
                artRect = ArtRect.FULL_LENGTH_LEFT;
            } else if (card.getSubtype(game).contains(SubType.SAGA)) {
                artRect = ArtRect.FULL_LENGTH_RIGHT;
            }

            // Frame color
            this.frameColor = card.getFrameColor(game).copy();

            // Frame style
            this.frameStyle = card.getFrameStyle();

            // Get starting loyalty
            this.startingLoyalty = CardUtil.convertLoyaltyOrDefense(card.getStartingLoyalty());

            // Get starting defense
            this.startingDefense = CardUtil.convertLoyaltyOrDefense(card.getStartingDefense());

            // add card icons at the end, so it will have full card view data
            this.generateCardIcons(null, card, game);
        }
    }

    /**
     * Generate card icons for current object (support card, permanent or stack ability)
     *
     * @param ability only for stack ability, all other must use null
     * @param object  original card/permanent/source
     */
    final protected void generateCardIcons(Ability ability, MageObject object, Game game) {
        if (object instanceof Permanent) {
            this.generateCardIconsForPermanent((Permanent) object, game);
        }
        this.generateCardIconsForAny(object, ability, game);
    }

    private void generateCardIconsForPermanent(Permanent permanent, Game game) {
        // card icons for permanents on battlefield
        if (game == null) {
            return;
        }

        // icon - all from abilities
        permanent.getAbilities(game).forEach(ability -> {
            this.cardIcons.addAll(ability.getIcons(game));
        });

        // icon - face down
        if (permanent.isFaceDown(game)) {
            this.cardIcons.add(CardIconImpl.FACE_DOWN);
        }

        // icon - commander
        Player owner = game.getPlayer(game.getOwnerId(permanent));
        if (owner != null && game.isCommanderObject(owner, permanent)) {
            this.cardIcons.add(CardIconImpl.COMMANDER);
        }

        // icon - ring-bearer
        if (permanent.isRingBearer()) {
            this.cardIcons.add(CardIconImpl.RINGBEARER);
        }

        // icon - restrictions (search it in card hints)
        List<String> restricts = new ArrayList<>();
        this.rules.forEach(r -> {
            if (r.startsWith(HintUtils.HINT_ICON_RESTRICT)
                    || r.startsWith(HintUtils.HINT_ICON_REQUIRE)) {
                restricts.add(r
                        .replace(HintUtils.HINT_ICON_RESTRICT, "")
                        .replace(HintUtils.HINT_ICON_REQUIRE, "")
                        .trim()
                );
            }
        });
        if (!restricts.isEmpty()) {
            restricts.sort(String::compareTo);
            this.cardIcons.add(new CardIconImpl(CardIconType.OTHER_HAS_RESTRICTIONS, String.join("<br>", restricts)));
        }
    }

    private void generateCardIconsForAny(MageObject object, Ability ability, Game game) {
        if (game == null) {
            return;
        }

        Card showCard = (object instanceof Card) ? (Card) object : null;

        Zone showZone;
        if (ability instanceof StackAbility) {
            showZone = Zone.STACK;
        } else {
            showZone = game.getState().getZone(object.getId());
        }
        if (showZone == null) {
            return;
        }

        Ability showAbility;
        if (ability != null) {
            showAbility = ability;
        } else if (showCard != null) {
            showAbility = showCard.getSpellAbility();
        } else {
            showAbility = null;
        }

        // icon - x cost
        if (showCard != null
                && showCard.getManaCost().containsX()
                && showAbility != null
                && (showZone.match(Zone.BATTLEFIELD) || showZone.match(Zone.STACK))) {
            int costX;
            if (showCard instanceof Permanent) {
                // permanent on battlefield (can show x icon multiple turns, so use end_game source)
                costX = ManacostVariableValue.END_GAME.calculate(game, showAbility, null);
            } else {
                // other like Stack (can show x icon on stack only, so use normal source)
                costX = ManacostVariableValue.REGULAR.calculate(game, showAbility, null);
            }
            this.cardIcons.add(CardIconImpl.variableCost(costX));
        }

        // icon - targets in stack
        if (showZone.match(Zone.STACK) && this.getTargets() != null && !this.getTargets().isEmpty()) {
            List<String> targets = new ArrayList<>();
            this.getTargets()
                    .stream()
                    .map(t -> {
                        String info;
                        MageObject targetObject = game.getObject(t);
                        if (targetObject != null) {
                            info = targetObject.getIdName();
                        } else {
                            Player targetPlayer = game.getPlayer(t);
                            if (targetPlayer != null) {
                                info = targetPlayer.getName();
                            } else {
                                info = "Unknown";
                            }
                        }
                        return info;
                    })
                    .sorted()
                    .forEach(targets::add);

            this.cardIcons.add(new CardIconImpl(
                    CardIconType.OTHER_HAS_TARGETS,
                    String.format("Has %d target(s). Move mouse over card to see target arrows:", this.getTargets().size())
                            + "<br><br>" + String.join("<br>", targets),
                    "T-" + this.getTargets().size()
            ));
        }
    }

    @Deprecated // TODO: research and raplace all usages to normal calls, see constructors for EmblemView and other
    public CardView(MageObject object, Game game) {
        super(object.getId(), object.getExpansionSetCode(), object.getCardNumber(), false, true);
        this.setOriginalValues(object);

        this.imageFileName = object.getImageFileName();
        this.imageNumber = object.getImageNumber();

        this.name = object.getName();
        this.displayName = object.getName();
        this.displayFullName = object.getName();
        if (object instanceof Permanent) {
            this.mageObjectType = MageObjectType.PERMANENT;
            this.power = Integer.toString(object.getPower().getValue());
            this.toughness = Integer.toString(object.getToughness().getValue());
            this.loyalty = Integer.toString(((Permanent) object).getCounters((Game) null).getCount(CounterType.LOYALTY));
            this.defense = Integer.toString(((Permanent) object).getCounters((Game) null).getCount(CounterType.DEFENSE));
        } else {
            this.power = object.getPower().toString();
            this.toughness = object.getToughness().toString();
            this.loyalty = "";
            this.defense = "";
        }
        this.cardTypes = new ArrayList<>(object.getCardType(game));
        this.subTypes = object.getSubtype(game).copy();
        this.superTypes = new ArrayList<>(object.getSuperType(game));
        this.color = object.getColor(game).copy();
        this.manaCostLeftStr = object.getManaCostSymbols();
        this.manaCostRightStr = new ArrayList<>();
        this.manaValue = object.getManaCost().manaValue();
        if (object instanceof PermanentToken) {
            this.mageObjectType = MageObjectType.TOKEN;
            PermanentToken permanentToken = (PermanentToken) object;
            this.rarity = Rarity.SPECIAL;
            this.rules = new ArrayList<>(permanentToken.getRules(game));
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
        }
        if (object.getSubtype().contains(SubType.CASE) ||
                object.getSubtype().contains(SubType.CLASS)) {
            artRect = ArtRect.FULL_LENGTH_LEFT;
        } else if (object.getSubtype().contains(SubType.SAGA)) {
            artRect = ArtRect.FULL_LENGTH_RIGHT;
        }
        // Frame color
        this.frameColor = object.getFrameColor(game).copy();
        // Frame style
        this.frameStyle = object.getFrameStyle();
        // Starting loyalty
        this.startingLoyalty = CardUtil.convertLoyaltyOrDefense(object.getStartingLoyalty());
        // Starting defense
        this.startingDefense = CardUtil.convertLoyaltyOrDefense(object.getStartingDefense());
    }

    protected CardView() {
        super(null, "", "0", false, true);
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
        this.cardNumber = emblem.getCardNumber();
        this.imageFileName = emblem.getImageFileName();
        this.imageNumber = emblem.getImageNumber();
        this.usesVariousArt = emblem.getUsesVariousArt();
        this.rarity = Rarity.SPECIAL;

        this.playableStats = emblem.playableStats.copy();
        this.isChoosable = emblem.isChoosable();
        this.isSelected = emblem.isSelected();
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
        this.cardNumber = "";
        this.imageFileName = "";
        this.imageNumber = 0;
        this.rarity = Rarity.SPECIAL;

        this.playableStats = dungeon.playableStats.copy();
        this.isChoosable = dungeon.isChoosable();
        this.isSelected = dungeon.isSelected();
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
        this.cardNumber = "";
        this.imageFileName = "";
        this.imageNumber = 0;
        this.rarity = Rarity.SPECIAL;

        this.playableStats = plane.playableStats.copy();
        this.isChoosable = plane.isChoosable();
        this.isSelected = plane.isSelected();
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
        this.cardNumber = designation.getCardNumber();
        this.expansionSetCode = designation.getExpansionSetCode();
        this.cardNumber = "";
        this.imageFileName = "";
        this.imageNumber = 0;
        this.rarity = Rarity.SPECIAL;
        // no playable/chooseable marks for designations
    }

    public CardView(boolean empty) {
        super(null, "", "0", false);
        if (!empty) {
            throw new IllegalArgumentException("Not supported.");
        }
        fillEmptyWithImageInfo(null, null, false);
    }

    public static boolean cardViewEquals(CardView a, CardView b) { // TODO: This belongs in CardView
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.getClass() != b.getClass()) {
            return false;
        }

        if (!(a.getDisplayName().equals(b.getDisplayName()) // TODO: Original code not checking everything. Why is it only checking these values?
                && a.getPower().equals(b.getPower())
                && a.getToughness().equals(b.getToughness())
                && a.getLoyalty().equals(b.getLoyalty())
                && a.getDefense().equals(b.getDefense())
                && 0 == a.getColor().compareTo(b.getColor())
                && a.getCardTypes().equals(b.getCardTypes())
                && a.getSubTypes().equals(b.getSubTypes())
                && a.getSuperTypes().equals(b.getSuperTypes())
                && a.getManaCostStr().equals(b.getManaCostStr())
                && a.getRules().equals(b.getRules())
                && Objects.equals(a.getRarity(), b.getRarity())

                && a.getFrameStyle() == b.getFrameStyle()
                && Objects.equals(a.getCounters(), b.getCounters())
                && a.isFaceDown() == b.isFaceDown())) {
            return false;
        }

        if (!(Objects.equals(a.getExpansionSetCode(), b.getExpansionSetCode())
                && Objects.equals(a.getCardNumber(), b.getCardNumber())
                && Objects.equals(a.getImageNumber(), b.getImageNumber())
                && Objects.equals(a.getImageFileName(), b.getImageFileName())
        )) {
            return false;
        }

        if (!(a instanceof PermanentView)) {
            return true;
        }
        PermanentView aa = (PermanentView) a;
        PermanentView bb = (PermanentView) b;
        return aa.hasSummoningSickness() == bb.hasSummoningSickness()
                && aa.getDamage() == bb.getDamage();
    }

    private void fillEmptyWithImageInfo(Game game, Card imageSourceCard, boolean isFaceDown) {
        this.name = "";
        this.displayName = "";
        this.displayFullName = "";
        this.expansionSetCode = "";
        this.cardNumber = "0";
        this.imageFileName = "";
        this.imageNumber = 0;
        this.usesVariousArt = false;

        this.rules = new ArrayList<>();
        this.power = "";
        this.toughness = "";
        this.loyalty = "";
        this.startingLoyalty = "";
        this.defense = "";
        this.startingDefense = "";
        this.cardTypes = new ArrayList<>();
        this.subTypes = new SubTypes();
        this.superTypes = new ArrayList<>();
        this.color = new ObjectColor();
        this.frameColor = new ObjectColor();
        this.frameStyle = FrameStyle.M15_NORMAL;
        this.manaCostLeftStr = new ArrayList<>();
        this.manaCostRightStr = new ArrayList<>();
        this.manaValue = 0;
        this.rarity = Rarity.SPECIAL; // hide rarity info

        if (imageSourceCard != null) {
            // keep inner images info (server side card already contain actual info)
            String imageSetCode = imageSourceCard.getExpansionSetCode();
            String imageCardNumber = imageSourceCard.getCardNumber();
            String imageFileName = imageSourceCard.getImageFileName();
            Integer imageNumber = imageSourceCard.getImageNumber();
            boolean imageUsesVariousArt = imageSourceCard.getUsesVariousArt();
            if (imageSetCode.equals(TokenRepository.XMAGE_TOKENS_SET_CODE)) {
                this.expansionSetCode = imageSetCode;
                this.cardNumber = imageCardNumber;
                this.imageFileName = imageFileName;
                this.imageNumber = imageNumber;
                this.usesVariousArt = imageUsesVariousArt;
            }

            if (imageSourceCard instanceof PermanentToken) {
                this.mageObjectType = MageObjectType.TOKEN;
            } else if (imageSourceCard instanceof Permanent) {
                this.mageObjectType = MageObjectType.PERMANENT;
            } else if (imageSourceCard.isCopy()) {
                this.mageObjectType = MageObjectType.COPY_CARD;
            } else if (imageSourceCard instanceof Spell) {
                this.mageObjectType = MageObjectType.SPELL;
            } else {
                this.mageObjectType = MageObjectType.CARD;
            }
        }

        // make default face down image
        // TODO: implement diff backface images someday and insert here (user data + card owner)
        if (isFaceDown && this.imageFileName.isEmpty()) {
            this.name = "";
            this.displayName = this.name;
            this.displayFullName = this.name;

            // as foretell face down
            // TODO: it's not ok to use that code - server side objects must has all data, see BecomesFaceDownCreatureEffect.makeFaceDownObject
            //  it must be a more global bug for card characteristics, not client side viewer
            if (game != null && imageSourceCard != null && ForetellAbility.isCardInForetell(imageSourceCard, game)) {
                TokenInfo tokenInfo = TokenRepository.instance.findPreferredTokenInfoForXmage(TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_FORETELL, this.getId());
                if (tokenInfo != null) {
                    this.expansionSetCode = tokenInfo.getSetCode();
                    this.cardNumber = "0";
                    this.imageFileName = tokenInfo.getName();
                    this.imageNumber = tokenInfo.getImageNumber();
                    this.usesVariousArt = false;
                }
                return;
            }

            // as normal face down
            TokenInfo tokenInfo = TokenRepository.instance.findPreferredTokenInfoForXmage(TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MANUAL, this.getId());
            if (tokenInfo != null) {
                this.expansionSetCode = tokenInfo.getSetCode();
                this.cardNumber = "0";
                this.imageFileName = tokenInfo.getName();
                this.imageNumber = tokenInfo.getImageNumber();
                this.usesVariousArt = false;
            }
        }
    }

    CardView(Token token, Game game) {
        super(token.getId(), "", "0", false);
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
        this.defense = "";
        this.startingDefense = "";
        this.cardTypes = new ArrayList<>(token.getCardType(game));
        this.subTypes = token.getSubtype(game).copy();
        this.superTypes = new ArrayList<>(token.getSuperType(game));
        this.color = token.getColor(game).copy();
        this.frameColor = token.getFrameColor(game).copy();
        this.frameStyle = token.getFrameStyle();
        this.manaCostLeftStr = token.getManaCostSymbols();
        this.manaCostRightStr = new ArrayList<>();
        this.rarity = Rarity.SPECIAL;

        this.expansionSetCode = token.getExpansionSetCode();
        this.cardNumber = token.getCardNumber();
        this.imageFileName = token.getImageFileName();
        this.imageNumber = token.getImageNumber();
    }

    protected final void addTargets(Targets targets, Effects effects, Ability source, Game game) {
        if (this.targets == null) {
            this.targets = new ArrayList<>();
        }

        // need only unique targets for arrow drawning
        Set<UUID> newTargets = new HashSet<>();

        // from normal targets
        for (Target target : targets) {
            if (target.isChosen(game)) {
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

    private void setOriginalValues(MageObject object) {
        if (object == null) {
            return;
        }
        // only valid objects to transfer original values are Card and Token
        if (object instanceof Card || object instanceof Token) {
            this.originalPower = object.getPower();
            this.originalToughness = object.getToughness();
            this.originalIsCopy = object.isCopy();
            if (object instanceof Card) {
                this.originalColorIdentity = findColorIdentityStr(((Card) object).getColorIdentity());
            } else {
                this.originalColorIdentity = findColorIdentityStr(ManaUtil.getColorIdentity((Token) object));
            }
        }
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

    public String getDefense() {
        return defense;
    }

    public String getStartingDefense() {
        return startingDefense;
    }

    public List<CardType> getCardTypes() {
        return cardTypes;
    }

    public SubTypes getSubTypes() {
        return subTypes;
    }

    public List<SuperType> getSuperTypes() {
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
        return CardUtil.concatManaSymbols(
                CardInfo.SPLIT_MANA_SEPARATOR_FULL,
                String.join("", this.manaCostLeftStr),
                String.join("", this.manaCostRightStr)
        );
    }

    public List<String> getManaCostSymbols() {
        List<String> symbols = new ArrayList<>();
        for (String symbol : this.manaCostLeftStr) {
            symbols.add(symbol);
        }
        for (String symbol : this.manaCostRightStr) {
            symbols.add(symbol);
        }
        return symbols;
    }

    public int getManaValue() {
        return manaValue;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String findColorIdentityStr(FilterMana colorInfo) {
        if (colorInfo == null) {
            colorInfo = new FilterMana();
        }

        String colorRes;
        if (colorInfo.getColorCount() == 0) {
            colorRes = "{C}";
        } else {
            colorRes = colorInfo.toString();
        }

        return CardUtil.concatManaSymbols(CardInfo.SPLIT_MANA_SEPARATOR_FULL, colorRes, "");
    }

    @Override
    public String getExpansionSetCode() {
        if (expansionSetCode == null) {
            expansionSetCode = "";
        }
        return expansionSetCode;
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

    public String getImageFileName() {
        return imageFileName;
    }

    public int getImageNumber() {
        return imageNumber;
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

    public boolean isPlaneswalker() {
        return cardTypes.contains(CardType.PLANESWALKER);
    }

    public boolean isBattle() {
        return cardTypes.contains(CardType.BATTLE);
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

    public boolean isExtraDeckCard() {
        return this.extraDeckCard;
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

    public boolean isKindred() {
        return cardTypes.contains(CardType.KINDRED);
    }

    public void setInViewerOnly(boolean inViewerOnly) {
        this.inViewerOnly = inViewerOnly;
    }

    public boolean inViewerOnly() {
        return inViewerOnly;
    }

    public MageInt getOriginalPower() {
        return this.originalPower;
    }

    public MageInt getOriginalToughness() {
        return this.originalToughness;
    }

    public String getOriginalColorIdentity() {
        return this.originalColorIdentity != null ? this.originalColorIdentity : "";
    }

    public boolean isOriginalACopy() {
        return this.originalIsCopy;
    }

    public List<CardIcon> getCardIcons() {
        return this.cardIcons;
    }

    public boolean showPT() {
        return this.isCreature() || this.getSubTypes().contains(SubType.VEHICLE);
    }

    public String getIdName() {
        return getName() + " [" + getId().toString().substring(0, 3) + ']';
    }
}
