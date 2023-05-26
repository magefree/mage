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
import mage.game.events.CreateTokenEvent;
import mage.game.events.CreatedTokenEvent;
import mage.game.events.CreatedTokensEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.Target;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Each token must have default constructor without params (GUI require for card viewer)
 */
public abstract class TokenImpl extends MageObjectImpl implements Token {

    protected String description;
    private final ArrayList<UUID> lastAddedTokenIds = new ArrayList<>();

    private Card copySourceCard; // the card the Token is a copy from
    private static final int MAX_TOKENS_PER_GAME = 500;

    protected Token backFace = null;
    private boolean entersTransformed = false;

    public TokenImpl() {
    }

    public TokenImpl(String name, String description) {
        this.name = name;
        this.description = description;

        // verify check: indefinite article
        if (description.startsWith("a ") || description.startsWith("an ")) {
            throw new IllegalArgumentException("Wrong code usage: don't use indefinite article here - " + description);
        }
    }

    protected TokenImpl(final TokenImpl token) {
        super(token);
        this.description = token.description;
        this.lastAddedTokenIds.addAll(token.lastAddedTokenIds);
        this.copySourceCard = token.copySourceCard; // will never be changed
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

        // TODO: remove all override and backFace changes (bug example: active transform ability in back face)
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

        // token from a card - must use card image instead (example: Embalm ability)
        if (!token.getCardNumber().isEmpty()) {
            return new TokenInfo(TokenType.TOKEN, token.getName(), token.getExpansionSetCode(), 0);
        }

        // token from another token
        if (token instanceof EmptyToken) {
            if (token.getExpansionSetCode() == null) {
                // possible reason: miss call of CardUtil.copySetAndCardNumber in copying method
                throw new IllegalArgumentException("Wrong code usage: can't copy token without set code");
            }
            return new TokenInfo(TokenType.TOKEN, token.getName(), token.getExpansionSetCode(), token.getImageNumber());
        }

        // token as is

        // source
        final String setCode;
        Card sourceCard = game.getCard(sourceId);
        if (sourceCard != null) {
            setCode = sourceCard.getExpansionSetCode();
        } else {
            MageObject sourceObject = game.getObject(sourceId);
            if (sourceObject != null) {
                setCode = sourceObject.getExpansionSetCode();
            } else {
                setCode = null;
            }
        }

        // search by set code
        TokenInfo foundInfo = TokenRepository.instance.findPreferredTokenInfoForClass(token.getClass().getName(), setCode);
        if (foundInfo != null) {
            return foundInfo;
        }

        // auto-image for creature token (it's a private token without official image, so try to find same paper image)
        if (token instanceof CreatureToken) {
            // TODO: return default creature token image
        }

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

        Set<PermanentToken> allAddedTokens = new HashSet<>();
        for (Map.Entry<Token, Integer> entry : event.getTokens().entrySet()) {
            Token token = entry.getKey();
            int amount = entry.getValue();

            // choose token's set code due source
            TokenInfo tokenInfo = TokenImpl.generateTokenInfo((TokenImpl) token, game, source == null ? null : source.getSourceId());
            token.setExpansionSetCode(tokenInfo.getSetCode());
            //token.setCardNumber(""); // if token from a card then don't change a card number
            token.setImageNumber(tokenInfo.getImageNumber());

            List<Permanent> needTokens = new ArrayList<>();
            List<Permanent> allowedTokens = new ArrayList<>();

            // prepare tokens to enter
            // must use same image for all tokens
            for (int i = 0; i < amount; i++) {
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
                    allAddedTokens.add((PermanentToken) permanent);
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
        CreatedTokensEvent.addEvents(allAddedTokens, source, game);

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
    public void setStartingLoyalty(int startingLoyalty) {
        if (backFace != null) {
            backFace.setStartingLoyalty(startingLoyalty);
        }
        super.setStartingLoyalty(startingLoyalty);
    }

    @Override
    public void setStartingDefense(int intArg) {
        if (backFace != null) {
            backFace.setStartingDefense(intArg);
        }
        super.setStartingDefense(intArg);
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
    public Token getBackFace() {
        return backFace;
    }

    @Override
    public void retainAllArtifactSubTypes(Game game) {
        if (backFace != null) {
            backFace.retainAllArtifactSubTypes(game);
        }
        super.retainAllArtifactSubTypes(game);
    }

    @Override
    public void retainAllEnchantmentSubTypes(Game game) {
        if (backFace != null) {
            backFace.retainAllEnchantmentSubTypes(game);
        }
        super.retainAllEnchantmentSubTypes(game);
    }

    @Override
    public void addSuperType(SuperType superType) {
        if (backFace != null) {
            backFace.addSuperType(superType);
        }
        super.addSuperType(superType);
    }

    @Override
    public void removeSuperType(SuperType superType) {
        if (backFace != null) {
            backFace.removeSuperType(superType);
        }
        super.removeSuperType(superType);
    }

    @Override
    public void addCardType(CardType... cardTypes) {
        if (backFace != null) {
            backFace.addCardType(cardTypes);
        }
        super.addCardType(cardTypes);
    }

    @Override
    public void removeCardType(CardType... cardTypes) {
        if (backFace != null) {
            backFace.removeCardType(cardTypes);
        }
        super.removeCardType(cardTypes);
    }

    @Override
    public void removeAllCardTypes() {
        if (backFace != null) {
            backFace.removeAllCardTypes();
        }
        super.removeAllCardTypes();
    }

    @Override
    public void removeAllCardTypes(Game game) {
        if (backFace != null) {
            backFace.removeAllCardTypes(game);
        }
        super.removeAllCardTypes(game);
    }

    @Override
    public void addSubType(SubType... subTypes) {
        if (backFace != null) {
            backFace.addSubType(subTypes);
        }
        super.addSubType(subTypes);
    }

    @Override
    public void removeAllSubTypes(Game game, SubTypeSet subTypeSet) {
        if (backFace != null) {
            backFace.removeAllSubTypes(game, subTypeSet);
        }
        super.removeAllSubTypes(game, subTypeSet);
    }

    @Override
    public void removeAllSubTypes(Game game) {
        if (backFace != null) {
            backFace.removeAllSubTypes(game);
        }
        super.removeAllSubTypes(game);
    }

    @Override
    public void retainAllLandSubTypes(Game game) {
        if (backFace != null) {
            backFace.retainAllLandSubTypes(game);
        }
        super.retainAllLandSubTypes(game);
    }

    @Override
    public void removeAllCreatureTypes(Game game) {
        if (backFace != null) {
            backFace.removeAllCreatureTypes(game);
        }
        super.removeAllCreatureTypes(game);
    }

    @Override
    public void removeAllCreatureTypes() {
        if (backFace != null) {
            backFace.removeAllCreatureTypes();
        }
        super.removeAllCreatureTypes();
    }

    @Override
    public void removeSubType(Game game, SubType subType) {
        if (backFace != null) {
            backFace.removeSubType(game, subType);
        }
        super.removeSubType(game, subType);
    }

    @Override
    public void setIsAllCreatureTypes(boolean value) {
        if (backFace != null) {
            backFace.setIsAllCreatureTypes(value);
        }
        super.setIsAllCreatureTypes(value);
    }

    @Override
    public void removePTCDA() {
        if (backFace != null) {
            backFace.removePTCDA();
        }
        super.removePTCDA();
    }

    @Override
    public void setName(String name) {
        if (backFace != null) {
            backFace.setName(name);
        }
        super.setName(name);
    }

    @Override
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

    public static TokenImpl createTokenByClassName(String fullClassName) {
        try {
            Class<?> c = Class.forName(fullClassName);
            Constructor<?> cons = c.getConstructor();
            Object newToken = cons.newInstance();
            if (newToken instanceof Token) {
                return (TokenImpl) newToken;
            }
        } catch (Exception e) {
            // ignore error
        }
        return null;
    }
}
