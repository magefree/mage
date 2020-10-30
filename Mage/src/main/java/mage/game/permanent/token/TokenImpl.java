package mage.game.permanent.token;

import mage.MageObject;
import mage.MageObjectImpl;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.CreatedTokenEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
        this.isAllCreatureTypes = token.isAllCreatureTypes;
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
    }

    @Override
    public boolean putOntoBattlefield(int amount, Game game, UUID sourceId, UUID controllerId) {
        return this.putOntoBattlefield(amount, game, sourceId, controllerId, false, false);
    }

    @Override
    public boolean putOntoBattlefield(int amount, Game game, UUID sourceId, UUID controllerId, boolean tapped, boolean attacking) {
        return putOntoBattlefield(amount, game, sourceId, controllerId, tapped, attacking, null);
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
    public boolean putOntoBattlefield(int amount, Game game, UUID sourceId, UUID controllerId, boolean tapped, boolean attacking, UUID attackedPlayer) {
        return putOntoBattlefield(amount, game, sourceId, controllerId, tapped, attacking, attackedPlayer, true);
    }

    @Override
    public boolean putOntoBattlefield(int amount, Game game, UUID sourceId, UUID controllerId, boolean tapped, boolean attacking, UUID attackedPlayer, boolean created) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        if (amount == 0) {
            return false;
        }
        lastAddedTokenIds.clear();

        CreateTokenEvent event = new CreateTokenEvent(sourceId, controllerId, amount, this);
        if (!created || !game.replaceEvent(event)) {
            putOntoBattlefieldHelper(event, game, tapped, attacking, attackedPlayer, created);
            return true;
        }
        return false;
    }

    private static void putOntoBattlefieldHelper(CreateTokenEvent event, Game game, boolean tapped, boolean attacking, UUID attackedPlayer, boolean created) {
        Player controller = game.getPlayer(event.getPlayerId());
        Token token = event.getToken();
        int amount = event.getAmount();

        List<Permanent> permanents = new ArrayList<>();
        List<Permanent> permanentsEntered = new ArrayList<>();

        String setCode = token instanceof TokenImpl ? ((TokenImpl) token).getSetCode(game, event.getSourceId()) : null;

        for (int i = 0; i < amount; i++) {
            PermanentToken newToken = new PermanentToken(token, event.getPlayerId(), setCode, game); // use event.getPlayerId() because it can be replaced by replacement effect
            game.getState().addCard(newToken);
            permanents.add(newToken);
            game.getPermanentsEntering().put(newToken.getId(), newToken);
            newToken.setTapped(tapped);
        }
        game.setScopeRelevant(true);
        for (Permanent permanent : permanents) {
            if (permanent.entersBattlefield(event.getSourceId(), game, Zone.OUTSIDE, true)) {
                permanentsEntered.add(permanent);
            } else {
                game.getPermanentsEntering().remove(permanent.getId());
            }
        }
        game.setScopeRelevant(false);
        int createOrder = game.getState().getNextPermanentOrderNumber();
        for (Permanent permanent : permanentsEntered) {
            game.addPermanent(permanent, createOrder);
            permanent.setZone(Zone.BATTLEFIELD, game);
            game.getPermanentsEntering().remove(permanent.getId());

            if (token instanceof TokenImpl) {
                ((TokenImpl) token).lastAddedTokenIds.add(permanent.getId());
                ((TokenImpl) token).lastAddedTokenId = permanent.getId();
            }
            game.addSimultaneousEvent(new ZoneChangeEvent(permanent, permanent.getControllerId(), Zone.OUTSIDE, Zone.BATTLEFIELD));
            if (permanent instanceof PermanentToken && created) {
                game.addSimultaneousEvent(new CreatedTokenEvent(event.getSourceId(), (PermanentToken) permanent));
            }
            if (attacking && game.getCombat() != null && game.getActivePlayerId().equals(permanent.getControllerId())) {
                game.getCombat().addAttackingCreature(permanent.getId(), game, attackedPlayer);
            }
            if (created) {
                game.informPlayers(controller.getLogName() + " creates a " + permanent.getLogName() + " token");
            } else {
                game.informPlayers(permanent.getLogName() + " enters the battlefield as a token under " + controller.getLogName() + "'s control'");
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
