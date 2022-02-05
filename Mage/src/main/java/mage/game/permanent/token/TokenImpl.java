package mage.game.permanent.token;

import mage.MageObject;
import mage.MageObjectImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.CreatedTokenEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.Target;
import mage.util.RandomUtil;

import java.util.*;

/**
 * Each token must have default constructor without params (GUI require for card viewer)
 */
public abstract class TokenImpl extends MageObjectImpl implements Token {

    protected String description;
    private final ArrayList<UUID> lastAddedTokenIds = new ArrayList<>();
    private UUID lastAddedTokenId;
    private int tokenType;
    private String originalCardNumber;
    private String originalExpansionSetCode;
    private String tokenDescriptor;
    private boolean expansionSetCodeChecked;
    private Card copySourceCard; // the card the Token is a copy from
    private static final int MAX_TOKENS_PER_GAME = 500;

    // list of set codes token images are available for
    protected List<String> availableImageSetCodes = new ArrayList<>();

    public enum Type {

        FIRST(1),
        SECOND(2);

        int code;

        Type(int code) {
            this.code = code;
        }

        int getCode() {
            return this.code;
        }
    }

    public TokenImpl() {
    }

    public TokenImpl(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public TokenImpl(String name, String description, int power, int toughness) {
        this(name, description);
        this.power.modifyBaseValue(power);
        this.toughness.modifyBaseValue(toughness);
    }

    public TokenImpl(final TokenImpl token) {
        super(token);
        this.description = token.description;
        this.tokenType = token.tokenType;
        this.lastAddedTokenId = token.lastAddedTokenId;
        this.lastAddedTokenIds.addAll(token.lastAddedTokenIds);
        this.originalCardNumber = token.originalCardNumber;
        this.originalExpansionSetCode = token.originalExpansionSetCode;
        this.expansionSetCodeChecked = token.expansionSetCodeChecked;
        this.copySourceCard = token.copySourceCard; // will never be changed
        this.availableImageSetCodes = token.availableImageSetCodes;
    }

    @Override
    public abstract Token copy();

    private void setTokenDescriptor() {
        this.tokenDescriptor = tokenDescriptor();
    }

    @Override
    public String getTokenDescriptor() {
        this.tokenDescriptor = tokenDescriptor();
        return tokenDescriptor;
    }

    private String tokenDescriptor() {
        String strName = this.name.replaceAll("[^a-zA-Z0-9]", "");
        String strColor = this.color.toString().replaceAll("[^a-zA-Z0-9]", "");
        String strSubtype = this.subtype.toString().replaceAll("[^a-zA-Z0-9]", "");
        String strCardType = this.cardType.toString().replaceAll("[^a-zA-Z0-9]", "");
        String descriptor = strName + '.' + strColor + '.' + strSubtype + '.' + strCardType + '.' + this.power + '.' + this.toughness;
        descriptor = descriptor.toUpperCase(Locale.ENGLISH);
        return descriptor;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public UUID getLastAddedToken() {
        return lastAddedTokenId;
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

    private String getSetCode(Game game, UUID sourceId) {
        // moved here from CreateTokenEffect because not all cards that create tokens use CreateTokenEffect
        // they use putOntoBattlefield directly
        // TODO: Check this setCode handling because it makes no sense if token put into play with e.g. "Feldon of the third Path"
        String setCode = null;
        if (this.getOriginalExpansionSetCode() != null && !this.getOriginalExpansionSetCode().isEmpty()) {
            setCode = this.getOriginalExpansionSetCode();
        } else {
            Card source = game.getCard(sourceId);
            if (source != null) {
                setCode = source.getExpansionSetCode();
            } else {
                MageObject object = game.getObject(sourceId);
                if (object instanceof PermanentToken) {
                    setCode = ((PermanentToken) object).getExpansionSetCode();
                }
            }
        }

        if (!expansionSetCodeChecked) {
            expansionSetCodeChecked = this.updateExpansionSetCode(setCode);
        }
        return setCode;
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
            String setCode = token instanceof TokenImpl ? ((TokenImpl) token).getSetCode(game, event.getSourceId()) : null;

            List<Permanent> needTokens = new ArrayList<>();
            List<Permanent> allowedTokens = new ArrayList<>();

            // prepare tokens to enter
            for (int i = 0; i < amount; i++) {
                // use event.getPlayerId() as controller cause it can be replaced by replacement effect
                PermanentToken newPermanent = new PermanentToken(token, event.getPlayerId(), setCode, game);
                game.getState().addCard(newPermanent);
                needTokens.add(newPermanent);
                game.getPermanentsEntering().put(newPermanent.getId(), newPermanent);
                newPermanent.setTapped(tapped);

                ZoneChangeEvent emptyEvent = new ZoneChangeEvent(newPermanent, newPermanent.getControllerId(), Zone.OUTSIDE, Zone.BATTLEFIELD);
                // tokens zcc must simulate card's zcc too keep copied card/spell settings
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
                    ((TokenImpl) token).lastAddedTokenId = permanent.getId();
                }

                // created token events
                ZoneChangeEvent zccEvent = new ZoneChangeEvent(permanent, permanent.getControllerId(), Zone.OUTSIDE, Zone.BATTLEFIELD);
                game.addSimultaneousEvent(zccEvent);
                if (permanent instanceof PermanentToken && created) {
                    game.addSimultaneousEvent(new CreatedTokenEvent(source, (PermanentToken) permanent));
                }

                // handle auras coming into the battlefield
                // code refactored from CopyPermanentEffect
                if (permanent.getSubtype().contains(SubType.AURA)) {
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
                    if (!controller.choose(auraOutcome, auraTarget, source.getSourceId(), game)) {
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
        this.power.setValue(power);
    }

    @Override
    public void setToughness(int toughness) {
        this.toughness.setValue(toughness);
    }

    @Override
    public int getTokenType() {
        return tokenType;
    }

    /**
     * Set token index to search in card-pictures-tok.txt (if set have multiple
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
    public void setOriginalExpansionSetCode(String originalExpansionSetCode) {
        // TODO: remove original set code at all... token image must be takes by card source or by latest set (on null source)
        // TODO: if set have same tokens then selects it by random
        this.originalExpansionSetCode = originalExpansionSetCode;
        setTokenDescriptor();
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
        if (!availableImageSetCodes.isEmpty()) {
            if (availableImageSetCodes.contains(code)) {
                setOriginalExpansionSetCode(code);
            } else // we should not set random set if appropriate set is already used
            {
                if (getOriginalExpansionSetCode() == null || getOriginalExpansionSetCode().isEmpty()
                        || !availableImageSetCodes.contains(getOriginalExpansionSetCode())) {
                    setOriginalExpansionSetCode(availableImageSetCodes.get(RandomUtil.nextInt(availableImageSetCodes.size())));
                }
            }
        } else if (getOriginalExpansionSetCode() == null || getOriginalExpansionSetCode().isEmpty()) {
            setOriginalExpansionSetCode(code);
        }
        setTokenDescriptor();
    }

    @Override
    public boolean updateExpansionSetCode(String setCode) {
        if (setCode == null || setCode.isEmpty()) {
            return false;
        }
        this.setExpansionSetCodeForImage(setCode);
        return true;
    }
}
