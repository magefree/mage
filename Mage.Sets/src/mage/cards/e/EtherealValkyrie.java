package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SpellAbilityCastMode;
import mage.constants.SpellAbilityType;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 * @author jeffwadsworth
 */
public final class EtherealValkyrie extends CardImpl {

    public EtherealValkyrie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ethereal Valkyrie enters the battlefield or attacks, draw a card, then exile a card from your hand face down. It becomes foretold. Its foretell cost is its mana cost reduced by {2}.
        this.addAbility(new EtherealValkyrieTriggeredAbility(new EtherealValkyrieEffect()));

    }

    private EtherealValkyrie(final EtherealValkyrie card) {
        super(card);
    }

    @Override
    public EtherealValkyrie copy() {
        return new EtherealValkyrie(this);
    }
}

class EtherealValkyrieTriggeredAbility extends TriggeredAbilityImpl {

    EtherealValkyrieTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    EtherealValkyrieTriggeredAbility(final EtherealValkyrieTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EtherealValkyrieTriggeredAbility copy() {
        return new EtherealValkyrieTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent p = game.getPermanent(event.getSourceId());
        Permanent pETB = game.getPermanent(event.getTargetId());
        if (p != null
                && p.getId() == sourceId) {
            return true;
        }
        if (pETB != null
                && pETB.getId() == sourceId) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters the battlefield or attacks, " + super.getRule();
    }
}

class EtherealValkyrieEffect extends OneShotEffect {

    public EtherealValkyrieEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw a card, then exile a card from your hand face down. It becomes foretold. Its foretell cost is its mana cost reduced by {2}";
    }

    public EtherealValkyrieEffect(final EtherealValkyrieEffect effect) {
        super(effect);
    }

    @Override
    public EtherealValkyrieEffect copy() {
        return new EtherealValkyrieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(1, source, game);
            TargetCardInHand targetCard = new TargetCardInHand(new FilterCard("card to exile face down.  It becomes foretold."));
            if (controller.chooseTarget(Outcome.Benefit, targetCard, source, game)) {
                Card exileCard = game.getCard(targetCard.getFirstTarget());
                if (exileCard == null) {
                    return false;
                }
                String foretellCost = CardUtil.reduceCost(exileCard.getSpellAbility().getManaCostsToPay(), 2).getText();
                game.getState().setValue(exileCard.getId().toString() + "Foretell Cost", foretellCost);
                game.getState().setValue(exileCard.getId().toString() + "Foretell Turn Number", game.getTurnNum());
                UUID exileId = CardUtil.getExileZoneId(exileCard.getId().toString() + "foretellAbility", game);
                controller.moveCardsToExile(exileCard, source, game, true, exileId, " Foretell Turn Number: " + game.getTurnNum());
                exileCard.setFaceDown(true, game);
                ForetellAbility foretellAbility = new ForetellAbility(exileCard, foretellCost);
                foretellAbility.setSourceId(exileCard.getId());
                foretellAbility.setControllerId(exileCard.getOwnerId());
                game.getState().addOtherAbility(exileCard, foretellAbility);
                foretellAbility.activate(game, true);
                game.addEffect(new ForetellAddCostEffect(new MageObjectReference(exileCard, game)), source);
                return true;
            }
        }
        return false;
    }
}

class ForetellAddCostEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;

    public ForetellAddCostEffect(MageObjectReference mor) {
        super(Duration.EndOfGame, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.mor = mor;
        staticText = "Foretold card";
    }

    public ForetellAddCostEffect(final ForetellAddCostEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = mor.getCard(game);
        if (card != null
                && game.getState().getZone(card.getId()) == Zone.EXILED) {
            String foretellCost = (String) game.getState().getValue(card.getId().toString() + "Foretell Cost");
            Ability ability = new ForetellCostAbility(foretellCost);
            ability.setSourceId(card.getId());
            ability.setControllerId(source.getControllerId());
            game.getState().addOtherAbility(card, ability);
        } else {
            discard();
        }
        return true;
    }

    @Override
    public ForetellAddCostEffect copy() {
        return new ForetellAddCostEffect(this);
    }
}

class ForetellCostAbility extends SpellAbility {

    private String abilityName;
    private SpellAbility spellAbilityToResolve;

    public ForetellCostAbility(String foretellCost) {
        super(null, "Testing", Zone.EXILED, SpellAbilityType.BASE_ALTERNATE, SpellAbilityCastMode.NORMAL);
        this.setAdditionalCostsRuleVisible(false);
        this.name = "Foretell " + foretellCost;
        this.addCost(new ManaCostsImpl(foretellCost));
    }

    public ForetellCostAbility(final ForetellCostAbility ability) {
        super(ability);
        this.spellAbilityType = ability.spellAbilityType;
        this.abilityName = ability.abilityName;
        this.spellAbilityToResolve = ability.spellAbilityToResolve;
    }

    @Override
    public ActivatedAbility.ActivationStatus canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game).canActivate()) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                // Card must be in the exile zone
                if (game.getState().getZone(card.getId()) != Zone.EXILED) {
                    return ActivatedAbility.ActivationStatus.getFalse();
                }
                // Card must be Foretold
                if (game.getState().getValue(card.getId().toString() + "Foretell Turn Number") == null
                        && game.getState().getValue(card.getId().toString() + "foretellAbility") == null) {
                    return ActivatedAbility.ActivationStatus.getFalse();
                }
                // Can't be cast if the turn it was Foretold is the same
                if ((int) game.getState().getValue(card.getId().toString() + "Foretell Turn Number") == game.getTurnNum()) {
                    return ActivatedAbility.ActivationStatus.getFalse();
                }
                // Check that the card is actually in the exile zone (ex: Oblivion Ring exiles it after it was Foretold, etc)
                UUID exileId = (UUID) game.getState().getValue(card.getId().toString() + "foretellAbility");
                ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
                if (exileZone != null
                        && exileZone.isEmpty()) {
                    return ActivatedAbility.ActivationStatus.getFalse();
                }
                if (card instanceof SplitCard) {
                    if (((SplitCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        return ((SplitCard) card).getLeftHalfCard().getSpellAbility().canActivate(playerId, game);
                    } else if (((SplitCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        return ((SplitCard) card).getRightHalfCard().getSpellAbility().canActivate(playerId, game);
                    }
                } else if (card instanceof ModalDoubleFacesCard) {
                    if (((ModalDoubleFacesCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        return ((ModalDoubleFacesCard) card).getLeftHalfCard().getSpellAbility().canActivate(playerId, game);
                    } else if (((ModalDoubleFacesCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        return ((ModalDoubleFacesCard) card).getRightHalfCard().getSpellAbility().canActivate(playerId, game);
                    }
                }
                return card.getSpellAbility().canActivate(playerId, game);
            }
        }
        return ActivatedAbility.ActivationStatus.getFalse();
    }

    @Override
    public SpellAbility getSpellAbilityToResolve(Game game) {
        Card card = game.getCard(getSourceId());
        if (card != null) {
            if (spellAbilityToResolve == null) {
                SpellAbility spellAbilityCopy = null;
                if (card instanceof SplitCard) {
                    if (((SplitCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((SplitCard) card).getLeftHalfCard().getSpellAbility().copy();
                    } else if (((SplitCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((SplitCard) card).getRightHalfCard().getSpellAbility().copy();
                    }
                } else if (card instanceof ModalDoubleFacesCard) {
                    if (((ModalDoubleFacesCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((ModalDoubleFacesCard) card).getLeftHalfCard().getSpellAbility().copy();
                    } else if (((ModalDoubleFacesCard) card).getRightHalfCard().getName().equals(abilityName)) {
                        spellAbilityCopy = ((ModalDoubleFacesCard) card).getRightHalfCard().getSpellAbility().copy();
                    }
                } else {
                    spellAbilityCopy = card.getSpellAbility().copy();
                }
                if (spellAbilityCopy == null) {
                    return null;
                }
                spellAbilityCopy.setId(this.getId());
                spellAbilityCopy.getManaCosts().clear();
                spellAbilityCopy.getManaCostsToPay().clear();
                spellAbilityCopy.getCosts().addAll(this.getCosts().copy());
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

    @Override
    public ForetellCostAbility copy() {
        return new ForetellCostAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return "";
    }

    /**
     * Used for split card in PlayerImpl method:
     * getOtherUseableActivatedAbilities
     *
     * @param abilityName
     */
    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

}
