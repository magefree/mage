package mage.game.permanent.token;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectImpl;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.repository.TokenInfo;
import mage.cards.repository.TokenRepository;
import mage.cards.repository.TokenType;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.events.CreateTokenEvent;
import mage.game.events.CreatedTokenEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.Target;
import mage.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Each token must have default constructor without params (GUI require for card viewer)
 */
public abstract class TokenImpl extends MageObjectImpl implements Token {

    protected String description;
    private final ArrayList<UUID> lastAddedTokenIds = new ArrayList<>();
    private int tokenType;
    private String originalCardNumber;
    private String originalExpansionSetCode;
    private Card copySourceCard; // the card the Token is a copy from
    private static final int MAX_TOKENS_PER_GAME = 500;

    // list of set codes token images are available for
    protected List<String> availableImageSetCodes = new ArrayList<>(); // TODO: delete

    protected Token backFace = null;
    private boolean entersTransformed = false;

    public TokenImpl() {
    }

    public TokenImpl(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected TokenImpl(final TokenImpl token) {
        super(token);
        this.description = token.description;
        this.tokenType = token.tokenType;
        this.lastAddedTokenIds.addAll(token.lastAddedTokenIds);
        this.originalCardNumber = token.originalCardNumber;
        this.originalExpansionSetCode = token.originalExpansionSetCode;
        this.copySourceCard = token.copySourceCard; // will never be changed
        this.availableImageSetCodes = token.availableImageSetCodes;
        this.backFace = token.backFace != null ? token.backFace.copy() : null;
        this.entersTransformed = token.entersTransformed;
    }

    @Override
    public abstract Token copy();

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<UUID> getLastAddedTokenIds() {
        return new ArrayList<>(lastAddedTokenIds);
    }

    @Override
    public void addAbility(Ability ability) {
        ability.setSourceId(this.getId());
        abilities.add(ability);
        abilities.addAll(ability.getSubAbilities());
        if (backFace != null) {
            backFace.addAbility(ability);
        }
    }

    // Directly from PermanentImpl
    @Override
    public void removeAbility(Ability abilityToRemove) {
        if (backFace != null) {
            backFace.removeAbility(abilityToRemove);
        }
        if (abilityToRemove == null) {
            return;
        }

        // 112.10b  Effects that remove an ability remove all instances of it.
        List<Ability> toRemove = new ArrayList<>();
        abilities.forEach(a -> {
            if (a.isSameInstance(abilityToRemove)) {
                toRemove.add(a);
            }
        });

        // TODO: what about triggered abilities? See addAbility above -- triggers adds to GameState
        toRemove.forEach(r -> abilities.remove(r));
    }

    // Directly from PermanentImpl
    @Override
    public void removeAbilities(List<Ability> abilitiesToRemove) {
        if (abilitiesToRemove == null) {
            return;
        }

        abilitiesToRemove.forEach(a -> removeAbility(a));
        if (backFace != null) {
            backFace.removeAbilities(abilitiesToRemove);
        }
    }

    @Override
    public boolean putOntoBattlefield(int amount, Game game, Ability source) {
        return this.putOntoBattlefield(amount, game, source, source.getControllerId());
    }

    @Override
    public boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId) {
        return this.putOntoBattlefield(amount, game, source, controllerId, false, false);
    }

    @Override
    public boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId, boolean tapped, boolean attacking) {
        return putOntoBattlefield(amount, game, source, controllerId, tapped, attacking, null);
    }

    public static TokenInfo generateTokenInfo(TokenImpl token, Game game, UUID sourceId) {
        // Choose a token image by priority:
        // - use source's set code
        // - use parent source's set code (too complicated, so ignore it)
        // - use random set code
        // - use default set code

        if (token.getOriginalCardNumber() != null) {
            // token from a card, so must use card image instead (example: Embalm ability)
            return new TokenInfo(TokenType.TOKEN, token.getName(), token.getOriginalExpansionSetCode(), 0);
        }

        // source
        final String setCode;
        Card sourceCard = game.getCard(sourceId);
        if (sourceCard != null) {
            setCode = sourceCard.getExpansionSetCode();
        } else {
            MageObject sourceObject = game.getObject(sourceId);
            if (sourceObject instanceof CommandObject) {
                setCode = ((CommandObject) sourceObject).getExpansionSetCodeForImage();
            } else {
                setCode = null;
            }
        }

        // search by set code
        List<TokenInfo> possibleInfo = TokenRepository.instance.getByClassName(token.getClass().getName())
                .stream()
                .filter(info -> info.getSetCode().equals(setCode))
                .collect(Collectors.toList());

        // search by random set
        if (possibleInfo.isEmpty()) {
            possibleInfo = new ArrayList<>(TokenRepository.instance.getByClassName(token.getClass().getName()));
        }

        if (possibleInfo.size() > 0) {
            return RandomUtil.randomFromCollection(possibleInfo);
        }

        // TODO: implement auto-generate images for CreatureToken (search public tokens for same characteristics)
        // TODO: implement Copy image
        // TODO: implement Manifest image
        // TODO: implement Morph image

        // unknown tokens
        return new TokenInfo(TokenType.TOKEN, "Unknown", TokenRepository.XMAGE_TOKENS_SET_CODE, 0);
    }

    @Override
    public boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId, boolean tapped, boolean attacking, UUID attackedPlayer) {
        return putOntoBattlefield(amount, game, source, controllerId, tapped, attacking, attackedPlayer, true);
    }

    @Override
    public boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId, boolean tapped, boolean attacking, UUID attackedPlayer, boolean created) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        if (amount == 0) {
            return false;
        }
        lastAddedTokenIds.clear();

        CreateTokenEvent event = new CreateTokenEvent(source, controllerId, amount, this);
        if (!created || !game.replaceEvent(event)) {
            int currentTokens = game.getBattlefield().countTokens(event.getPlayerId());
            int tokenSlots = Math.max(MAX_TOKENS_PER_GAME - currentTokens, 0);
            int amountToRemove = event.getAmount() - tokenSlots;
            if (amountToRemove > 0) {
                game.informPlayers(
                        "The token limit per player is " + MAX_TOKENS_PER_GAME + ", " + controller.getName()
                                + " will only create " + tokenSlots + " tokens."
                );
                Iterator<Map.Entry<Token, Integer>> it = event.getTokens().entrySet().iterator();
                while (it.hasNext() && amountToRemove > 0) {
                    Map.Entry<Token, Integer> entry = it.next();
                    int newValue = entry.getValue() - amountToRemove;
                    if (newValue > 0) {
                        entry.setValue(newValue);
                        break;
                    }
                    amountToRemove -= entry.getValue();
                    it.remove();
                }
            }
            putOntoBattlefieldHelper(event, game, source, tapped, attacking, attackedPlayer, created);
            event.getTokens()
                    .keySet()
                    .stream()
                    .map(Token::getLastAddedTokenIds)
                    .flatMap(Collection::stream)
                    .distinct()
                    .filter(uuid -> !this.lastAddedTokenIds.contains(uuid))
                    .forEach(this.lastAddedTokenIds::add);
            return true;
        }
        return false;
    }

    private static void putOntoBattlefieldHelper(CreateTokenEvent event, Game game, Ability source, boolean tapped, boolean attacking, UUID attackedPlayer, boolean created) {
        Player controller = game.getPlayer(event.getPlayerId());
        if (controller == null) {
            return;
        }

        for (Map.Entry<Token, Integer> entry : event.getTokens().entrySet()) {
            Token token = entry.getKey();
            int amount = entry.getValue();

            // choose token's set code due source
            TokenInfo tokenInfo = TokenImpl.generateTokenInfo((TokenImpl) token, game, source == null ? null : source.getSourceId());
            token.setOriginalExpansionSetCode(tokenInfo.getSetCode());
            token.setExpansionSetCodeForImage(tokenInfo.getSetCode());
            token.setTokenType(tokenInfo.getImageNumber());

            List<Permanent> needTokens = new ArrayList<>();
            List<Permanent> allowedTokens = new ArrayList<>();

            // prepare tokens to enter
            for (int i = 0; i < amount; i++) {
                // TODO: add random setTokenType here?
                // use event.getPlayerId() as controller because it can be replaced by replacement effect
                PermanentToken newPermanent = new PermanentToken(token, event.getPlayerId(), game);
                game.getState().addCard(newPermanent);
                needTokens.add(newPermanent);
                game.getPermanentsEntering().put(newPermanent.getId(), newPermanent);
                newPermanent.setTapped(tapped);

                ZoneChangeEvent emptyEvent = new ZoneChangeEvent(newPermanent, newPermanent.getControllerId(), Zone.OUTSIDE, Zone.BATTLEFIELD);
                // tokens zcc must simulate card's zcc to keep copied card/spell settings
                // (example: etb's kicker ability of copied creature spell, see tests with Deathforge Shaman)
                newPermanent.updateZoneChangeCounter(game, emptyEvent);
            }

            // check ETB effects
            game.setScopeRelevant(true);
            for (Permanent permanent : needTokens) {
                if (permanent.entersBattlefield(source, game, Zone.OUTSIDE, true)) {
                    allowedTokens.add(permanent);
                } else {
                    game.getPermanentsEntering().remove(permanent.getId());
                }
            }
            game.setScopeRelevant(false);

            // put allowed tokens to play
            int createOrder = game.getState().getNextPermanentOrderNumber();
            for (Permanent permanent : allowedTokens) {
                game.addPermanent(permanent, createOrder);
                permanent.setZone(Zone.BATTLEFIELD, game);
                game.getPermanentsEntering().remove(permanent.getId());

                // keep tokens ids
                if (token instanceof TokenImpl) {
                    ((TokenImpl) token).lastAddedTokenIds.add(permanent.getId());
                }

                // created token events
                ZoneChangeEvent zccEvent = new ZoneChangeEvent(permanent, permanent.getControllerId(), Zone.OUTSIDE, Zone.BATTLEFIELD);
                game.addSimultaneousEvent(zccEvent);
                if (permanent instanceof PermanentToken && created) {
                    game.addSimultaneousEvent(new CreatedTokenEvent(source, (PermanentToken) permanent));
                }

                // if token was created (not a spell copy) handle auras coming into the battlefield
                // code blindly copied from CopyPermanentEffect
                // TODO: clean this up -- half the comments make no sense in the context of creating a token
                if (created && permanent.getSubtype().contains(SubType.AURA)) {
                    Outcome auraOutcome = Outcome.BoostCreature;
                    Target auraTarget = null;

                    // attach - search effect in spell ability (example: cast Utopia Sprawl, cast Estrid's Invocation on it)
                    for (Ability ability : permanent.getAbilities()) {
                        if (!(ability instanceof SpellAbility)) {
                            continue;
                        }
                        auraOutcome = ability.getEffects().getOutcome(ability);
                        for (Effect effect : ability.getEffects()) {
                            if (!(effect instanceof AttachEffect)) {
                                continue;
                            }
                            if (permanent.getSpellAbility().getTargets().size() > 0) {
                                auraTarget = permanent.getSpellAbility().getTargets().get(0);
                            }
                        }
                    }

                    // enchant - search in all abilities (example: cast Estrid's Invocation on enchanted creature by Estrid, the Masked second ability, cast Estrid's Invocation on it)
                    if (auraTarget == null) {
                        for (Ability ability : permanent.getAbilities()) {
                            if (!(ability instanceof EnchantAbility)) {
                                continue;
                            }
                            auraOutcome = ability.getEffects().getOutcome(ability);
                            if (ability.getTargets().size() > 0) { // Animate Dead don't have targets
                                auraTarget = ability.getTargets().get(0);
                            }
                        }
                    }

                    // if this is a copy of a copy, the copy's target has been copied and needs to be cleared
                    if (auraTarget == null) {
                        break;
                    }
                    // clear selected target
                    if (auraTarget.getFirstTarget() != null) {
                        auraTarget.remove(auraTarget.getFirstTarget());
                    }

                    // select new target
                    auraTarget.setNotTarget(true);
                    if (!controller.choose(auraOutcome, auraTarget, source, game)) {
                        break;
                    }
                    UUID targetId = auraTarget.getFirstTarget();
                    Permanent targetPermanent = game.getPermanent(targetId);
                    Player targetPlayer = game.getPlayer(targetId);
                    if (targetPermanent != null) {
                        targetPermanent.addAttachment(permanent.getId(), source, game);
                    } else if (targetPlayer != null) {
                        targetPlayer.addAttachment(permanent.getId(), source, game);
                    }
                }
                // end of aura code : just remove this line if everything works out well

                // must attack
                if (attacking && game.getCombat() != null && game.getActivePlayerId().equals(permanent.getControllerId())) {
                    game.getCombat().addAttackingCreature(permanent.getId(), game, attackedPlayer);
                }

                // game logs
                if (created) {
                    game.informPlayers(controller.getLogName() + " creates a " + permanent.getLogName() + " token");
                } else {
                    game.informPlayers(permanent.getLogName() + " enters the battlefield as a token under " + controller.getLogName() + "'s control'");
                }
            }
        }
        game.getState().applyEffects(game); // Needed to do it here without LKIReset i.e. do get SwordOfTheMeekTest running correctly.
    }

    @Override
    public void setPower(int power) {
        if (this.backFace != null) {
            this.backFace.setPower(power);
        }
        this.power = new MageInt(power);
    }

    @Override
    public void setToughness(int toughness) {
        if (this.backFace != null) {
            this.backFace.setToughness(toughness);
        }
        this.toughness = new MageInt(toughness);
    }

    @Override
    public int getTokenType() {
        return tokenType;
    }

    /**
     * Set token index to search in tokens-database.txt (if set have multiple
     * tokens with same name) Default is 1
     */
    @Override
    public void setTokenType(int tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String getOriginalCardNumber() {
        return originalCardNumber;
    }

    @Override
    public void setOriginalCardNumber(String originalCardNumber) {
        this.originalCardNumber = originalCardNumber;
    }

    @Override
    public String getOriginalExpansionSetCode() {
        return originalExpansionSetCode;
    }

    @Override
    public void setStartingLoyalty(int startingLoyalty) {
        if (backFace != null) {
            backFace.setStartingLoyalty(startingLoyalty);
        }
        super.setStartingLoyalty(startingLoyalty);
    }

    public void setStartingDefense(int intArg) {
        if (backFace != null) {
            backFace.setStartingDefense(intArg);
        }
        super.setStartingDefense(intArg);
    }

    @Override
    public void setOriginalExpansionSetCode(String originalExpansionSetCode) {
        // TODO: delete
        // TODO: remove original set code at all... token image must be takes by card source or by latest set (on null source)
        // TODO: if set have same tokens then selects it by random
        this.originalExpansionSetCode = originalExpansionSetCode;
    }

    @Override
    public Card getCopySourceCard() {
        return copySourceCard;
    }

    @Override
    public void setCopySourceCard(Card copySourceCard) {
        if (copySourceCard != null) {
            this.copySourceCard = copySourceCard.copy();
        }
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        // TODO: delete
        setOriginalExpansionSetCode(code);
    }

    @Override
    public Token getBackFace() {
        return backFace;
    }

    public void retainAllArtifactSubTypes(Game game) {
        if (backFace != null) {
            backFace.retainAllArtifactSubTypes(game);
        }
        super.retainAllArtifactSubTypes(game);
    }

    public void retainAllEnchantmentSubTypes(Game game) {
        if (backFace != null) {
            backFace.retainAllEnchantmentSubTypes(game);
        }
        super.retainAllEnchantmentSubTypes(game);
    }

    public void addSuperType(SuperType superType) {
        if (backFace != null) {
            backFace.addSuperType(superType);
        }
        super.addSuperType(superType);
    }

    public void removeSuperType(SuperType superType) {
        if (backFace != null) {
            backFace.removeSuperType(superType);
        }
        super.removeSuperType(superType);
    }

    public void addCardType(CardType... cardTypes) {
        if (backFace != null) {
            backFace.addCardType(cardTypes);
        }
        super.addCardType(cardTypes);
    }

    public void removeCardType(CardType... cardTypes) {
        if (backFace != null) {
            backFace.removeCardType(cardTypes);
        }
        super.removeCardType(cardTypes);
    }

    public void removeAllCardTypes() {
        if (backFace != null) {
            backFace.removeAllCardTypes();
        }
        super.removeAllCardTypes();
    }

    public void removeAllCardTypes(Game game) {
        if (backFace != null) {
            backFace.removeAllCardTypes(game);
        }
        super.removeAllCardTypes(game);
    }

    public void addSubType(SubType... subTypes) {
        if (backFace != null) {
            backFace.addSubType(subTypes);
        }
        super.addSubType(subTypes);
    }

    public void removeAllSubTypes(Game game, SubTypeSet subTypeSet) {
        if (backFace != null) {
            backFace.removeAllSubTypes(game, subTypeSet);
        }
        super.removeAllSubTypes(game, subTypeSet);
    }

    public void removeAllSubTypes(Game game) {
        if (backFace != null) {
            backFace.removeAllSubTypes(game);
        }
        super.removeAllSubTypes(game);
    }

    public void retainAllLandSubTypes(Game game) {
        if (backFace != null) {
            backFace.retainAllLandSubTypes(game);
        }
        super.retainAllLandSubTypes(game);
    }

    public void removeAllCreatureTypes(Game game) {
        if (backFace != null) {
            backFace.removeAllCreatureTypes(game);
        }
        super.removeAllCreatureTypes(game);
    }

    public void removeAllCreatureTypes() {
        if (backFace != null) {
            backFace.removeAllCreatureTypes();
        }
        super.removeAllCreatureTypes();
    }

    public void removeSubType(Game game, SubType subType) {
        if (backFace != null) {
            backFace.removeSubType(game, subType);
        }
        super.removeSubType(game, subType);
    }

    public void setIsAllCreatureTypes(boolean value) {
        if (backFace != null) {
            backFace.setIsAllCreatureTypes(value);
        }
        super.setIsAllCreatureTypes(value);
    }

    public void removePTCDA() {
        if (backFace != null) {
            backFace.removePTCDA();
        }
        super.removePTCDA();
    }

    public String getName() {
        if (backFace != null) {
            backFace.getName();
        }
        return super.getName();
    }

    public void setName(String name) {
        if (backFace != null) {
            backFace.setName(name);
        }
        super.setName(name);
    }

    public void setColor(ObjectColor color) {
        if (backFace != null) {
            backFace.setColor(color);
        }
        this.getColor().setColor(color);
    }

    @Override
    public void clearManaCost() {
        if (backFace != null) {
            backFace.clearManaCost();
        }
        this.getManaCost().clear();
    }

    @Override
    public void setEntersTransformed(boolean entersTransformed) {
        this.entersTransformed = entersTransformed;
    }

    @Override
    public boolean isEntersTransformed() {
        return this.entersTransformed && this.backFace != null;
    }
}
