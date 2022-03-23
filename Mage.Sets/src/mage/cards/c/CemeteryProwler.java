package mage.cards.c;

import java.util.HashSet;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class CemeteryProwler extends CardImpl {

    public CemeteryProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Cemetery Prowler enters the battlefield or attacks, exile a card from a graveyard.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CemeteryProwlerExileEffect()));

        // Spells you cast cost {1} less to cast for each card type they share with cards exiled with Cemetery Prowler.
        this.addAbility(new SimpleStaticAbility(new CemeteryProwlerCostReductionEffect()));
    }

    private CemeteryProwler(final CemeteryProwler card) {
        super(card);
    }

    @Override
    public CemeteryProwler copy() {
        return new CemeteryProwler(this);
    }
}

class CemeteryProwlerExileEffect extends OneShotEffect {

    public CemeteryProwlerExileEffect() {
        super(Outcome.Exile);
        staticText = "exile a card from a graveyard";
    }

    private CemeteryProwlerExileEffect(final CemeteryProwlerExileEffect effect) {
        super(effect);
    }

    @Override
    public CemeteryProwlerExileEffect copy() {
        return new CemeteryProwlerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInGraveyard target = new TargetCardInGraveyard();
            target.setNotTarget(true);
            controller.choose(outcome, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                MageObject sourceObject = source.getSourceObject(game);
                String exileName = sourceObject == null ? null : sourceObject.getIdName();
                return controller.moveCardsToExile(card, source, game, true, exileId, exileName);
            }
        }
        return false;
    }
}

class CemeteryProwlerCostReductionEffect extends CostModificationEffectImpl {

    public CemeteryProwlerCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Spells you cast cost {1} less to cast for each card type they share with cards exiled with {this}";
    }

    private CemeteryProwlerCostReductionEffect(final CemeteryProwlerCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public CemeteryProwlerCostReductionEffect copy() {
        return new CemeteryProwlerCostReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (abilityToModify instanceof SpellAbility) {
            // sourceObjectZoneChangeCounter is not working here.  Getting it from GameState works.
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()));
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            Card castCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
            if (exileZone != null && castCard != null) {
                HashSet<CardType> cardTypes = new HashSet<>();
                for (UUID cardId : exileZone) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        cardTypes.addAll(card.getCardType(game));
                    }
                }
                int sharedTypes = 0;
                for (CardType type : castCard.getCardType(game)) {
                    if (cardTypes.contains(type)) {
                        sharedTypes++;
                    }
                }
                if (sharedTypes > 0) {
                    CardUtil.reduceCost(abilityToModify, sharedTypes);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.isControlledBy(source.getControllerId());
    }
}
