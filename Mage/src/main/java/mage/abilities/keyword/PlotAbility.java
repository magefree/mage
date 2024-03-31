package mage.abilities.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public class PlotAbility extends SpecialAction {

    private final String rule;

    public PlotAbility(String plotCost) {
        super(Zone.HAND);
        this.addCost(new ManaCostsImpl<>(plotCost));
        this.addEffect(new PlotSourceExileEffect());
        this.setTiming(TimingRule.SORCERY);
        this.usesStack = false;
        this.rule = "Plot " + plotCost;
    }

    private PlotAbility(final PlotAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public PlotAbility copy() {
        return new PlotAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }

    // TODO: handle [[Fblthp, Lost on the Range]] allowing player to plot from library.
    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // plot can only be activated from a hand
        // TODO: change that for Fblthp.
        if (game.getState().getZone(getSourceId()) != Zone.HAND) {
            return ActivationStatus.getFalse();
        }
        // suspend uses card's timing restriction
        Card card = game.getCard(getSourceId());
        if (card == null) {
            return ActivationStatus.getFalse();
        }
        if (!card.getSpellAbility().spellCanBeActivatedRegularlyNow(playerId, game)) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    static UUID getPlotExileId(UUID playerId, Game game) {
        UUID exileId = (UUID) game.getState().getValue("PlotExileId" + playerId.toString());
        if (exileId == null) {
            exileId = UUID.randomUUID();
            game.getState().setValue("PlotExileId" + playerId, exileId);
        }
        return exileId;
    }

    static String getPlotTurnKeyForCard(UUID cardId) {
        return cardId.toString() + "|" + "Plotted Turn";
    }

    /**
     * To be used in an OneShotEffect's apply.
     * 'Plot' the provided card. The card is exiled in it's owner plot zone,
     * and may be cast by that player without paying its mana cost at sorcery
     * speed on a future turn.
     */
    public static boolean doExileAndPlotCard(Card card, Game game, Ability source) {
        if (card == null) {
            return false;
        }
        Player owner = game.getPlayer(card.getOwnerId());
        if (owner == null) {
            return false;
        }
        UUID exileId = PlotAbility.getPlotExileId(owner.getId(), game);
        String exileZoneName = "Plots of " + owner.getName();
        Card mainCard = card.getMainCard();
        if (mainCard.moveToExile(exileId, exileZoneName, source, game)) {
            // Remember on which turn the card was last plotted.
            game.getState().setValue(PlotAbility.getPlotTurnKeyForCard(mainCard.getId()), game.getTurnNum());
            game.addEffect(new PlotAddSpellAbilityEffect(new MageObjectReference(mainCard, game)), source);
            game.informPlayers(owner.getLogName() + " plots " + mainCard.getLogName());
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.BECOME_PLOTTED, mainCard.getId(), source, owner.getId()));
        }
        return true;
    }
}

/**
 * Exile the source card in the plot exile zone of its owner
 * and allow its owner to cast it at sorcery speed starting
 * next turn.
 */
class PlotSourceExileEffect extends OneShotEffect {

    PlotSourceExileEffect() {
        super(Outcome.Benefit);
    }

    private PlotSourceExileEffect(final PlotSourceExileEffect effect) {
        super(effect);
    }

    @Override
    public PlotSourceExileEffect copy() {
        return new PlotSourceExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return PlotAbility.doExileAndPlotCard(game.getCard(source.getSourceId()), game, source);
    }
}

class PlotAddSpellAbilityEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;

    PlotAddSpellAbilityEffect(MageObjectReference mor) {
        super(Duration.EndOfGame, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.mor = mor;
        staticText = "Plot card";
    }

    private PlotAddSpellAbilityEffect(final PlotAddSpellAbilityEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public PlotAddSpellAbilityEffect copy() {
        return new PlotAddSpellAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = mor.getCard(game);
        if (card == null) {
            discard();
            return true;
        }

        Card mainCard = card.getMainCard();
        UUID mainCardId = mainCard.getId();
        Player player = game.getPlayer(card.getOwnerId());
        if (game.getState().getZone(mainCardId) != Zone.EXILED || player == null) {
            discard();
            return true;
        }

        List<Card> faces = CardUtil.getCastableComponents(mainCard, null, source, player, game, null, false);
        for (Card face : faces) {
            // Add the spell ability to each castable face to have the proper name/paramaters.
            PlotSpellAbility ability = new PlotSpellAbility(face.getName());
            ability.setSourceId(face.getId());
            ability.setControllerId(player.getId());
            ability.setSpellAbilityType(face.getSpellAbility().getSpellAbilityType());
            game.getState().addOtherAbility(face, ability);
        }
        return true;
    }
}

/**
 * This is inspired (after a little cleanup) by how {@link ForetellAbility} does it.
 */
class PlotSpellAbility extends SpellAbility {

    private String faceCardName; // Same as with Foretell, we identify the proper face with its spell name.
    private SpellAbility spellAbilityToResolve;

    PlotSpellAbility(String faceCardName) {
        super(null, faceCardName, Zone.EXILED, SpellAbilityType.BASE_ALTERNATE, SpellAbilityCastMode.PLOT);
        this.setRuleVisible(false);
        this.setAdditionalCostsRuleVisible(false);
        this.faceCardName = faceCardName;
        this.addCost(new ManaCostsImpl<>("{0}"));
    }

    private PlotSpellAbility(final PlotSpellAbility ability) {
        super(ability);
        this.faceCardName = ability.faceCardName;
        this.spellAbilityToResolve = ability.spellAbilityToResolve;
    }

    @Override
    public PlotSpellAbility copy() {
        return new PlotSpellAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game).canActivate()) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                Card mainCard = card.getMainCard();
                UUID mainCardId = mainCard.getId();
                // Card must be in the exile zone
                if (game.getState().getZone(mainCardId) != Zone.EXILED) {
                    return ActivationStatus.getFalse();
                }
                Integer plottedTurn = (Integer) game.getState().getValue(PlotAbility.getPlotTurnKeyForCard(mainCardId));
                // Card must have been plotted
                if (plottedTurn == null) {
                    return ActivationStatus.getFalse();
                }
                // Can't be cast if the turn it was last Plotted is the same
                if (plottedTurn == game.getTurnNum()) {
                    return ActivationStatus.getFalse();
                }
                // Only allow the cast at sorcery speed
                if (!game.canPlaySorcery(playerId)) {
                    return ActivationStatus.getFalse();
                }
                // Check that the proper face can be cast.
                // TODO: As with Foretell, this does not look very clean. Is the face card sometimes incorrect on calling canActivate?
                if (mainCard instanceof CardWithHalves) {
                    if (((CardWithHalves) mainCard).getLeftHalfCard().getName().equals(faceCardName)) {
                        return ((CardWithHalves) mainCard).getLeftHalfCard().getSpellAbility().canActivate(playerId, game);
                    } else if (((CardWithHalves) mainCard).getRightHalfCard().getName().equals(faceCardName)) {
                        return ((CardWithHalves) mainCard).getRightHalfCard().getSpellAbility().canActivate(playerId, game);
                    }
                } else if (card instanceof AdventureCard) {
                    if (card.getMainCard().getName().equals(faceCardName)) {
                        return card.getMainCard().getSpellAbility().canActivate(playerId, game);
                    } else if (((AdventureCard) card).getSpellCard().getName().equals(faceCardName)) {
                        return ((AdventureCard) card).getSpellCard().getSpellAbility().canActivate(playerId, game);
                    }
                }
                return card.getSpellAbility().canActivate(playerId, game);
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public SpellAbility getSpellAbilityToResolve(Game game) {
        Card card = game.getCard(getSourceId());
        if (card != null) {
            if (spellAbilityToResolve == null) {
                SpellAbility spellAbilityCopy = null;
                // TODO: As with Foretell, this does not look very clean. Is the face card sometimes incorrect on calling getSpellAbilityToResolve?
                if (card instanceof CardWithHalves) {
                    if (((CardWithHalves) card).getLeftHalfCard().getName().equals(faceCardName)) {
                        spellAbilityCopy = ((CardWithHalves) card).getLeftHalfCard().getSpellAbility().copy();
                    } else if (((CardWithHalves) card).getRightHalfCard().getName().equals(faceCardName)) {
                        spellAbilityCopy = ((CardWithHalves) card).getRightHalfCard().getSpellAbility().copy();
                    }
                } else if (card instanceof AdventureCard) {
                    if (card.getMainCard().getName().equals(faceCardName)) {
                        spellAbilityCopy = card.getMainCard().getSpellAbility().copy();
                    } else if (((AdventureCard) card).getSpellCard().getName().equals(faceCardName)) {
                        spellAbilityCopy = ((AdventureCard) card).getSpellCard().getSpellAbility().copy();
                    }
                } else {
                    spellAbilityCopy = card.getSpellAbility().copy();
                }
                if (spellAbilityCopy == null) {
                    return null;
                }
                spellAbilityCopy.setId(this.getId());
                spellAbilityCopy.clearManaCosts();
                spellAbilityCopy.clearManaCostsToPay();
                spellAbilityCopy.addCost(this.getCosts().copy());
                spellAbilityCopy.addCost(this.getManaCosts().copy());
                spellAbilityCopy.setSpellAbilityCastMode(this.getSpellAbilityCastMode());
                spellAbilityToResolve = spellAbilityCopy;
            }
        }
        return spellAbilityToResolve;
    }

    @Override
    public Costs<Cost> getCosts() {
        if (spellAbilityToResolve == null) {
            return super.getCosts();
        }
        return spellAbilityToResolve.getCosts();
    }
}