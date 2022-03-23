package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CouncilOfTheAbsolute extends CardImpl {

    public CouncilOfTheAbsolute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // As Council of the Absolute enters the battlefield, name a card other than a creature or a land card.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_AND_NON_CREATURE_NAME)));
        // Your opponents can't cast the chosen card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CouncilOfTheAbsoluteReplacementEffect()));
        // Spells with the chosen name cost 2 less for you to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CouncilOfTheAbsoluteCostReductionEffect()));

    }

    private CouncilOfTheAbsolute(final CouncilOfTheAbsolute card) {
        super(card);
    }

    @Override
    public CouncilOfTheAbsolute copy() {
        return new CouncilOfTheAbsolute(this);
    }

}

class CouncilOfTheAbsoluteReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    public CouncilOfTheAbsoluteReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Your opponents can't cast spells with the chosen name";
    }

    public CouncilOfTheAbsoluteReplacementEffect(final CouncilOfTheAbsoluteReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CouncilOfTheAbsoluteReplacementEffect copy() {
        return new CouncilOfTheAbsoluteReplacementEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast a spell with that name (" + mageObject.getName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            MageObject object = game.getObject(event.getSourceId());
            String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
            return object != null && cardName != null && CardUtil.haveSameNames(object, cardName, game);
        }
        return false;
    }
}

class CouncilOfTheAbsoluteCostReductionEffect extends CostModificationEffectImpl {

    public CouncilOfTheAbsoluteCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Spells with the chosen name you cast cost 2 less to cast";
    }

    protected CouncilOfTheAbsoluteCostReductionEffect(CouncilOfTheAbsoluteCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility)
                && abilityToModify.isControlledBy(source.getControllerId())) {
            Card card = game.getCard(abilityToModify.getSourceId());
            if (card != null) {
                String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
                return CardUtil.haveSameNames(card, cardName, game);
            }
        }
        return false;
    }

    @Override
    public CouncilOfTheAbsoluteCostReductionEffect copy() {
        return new CouncilOfTheAbsoluteCostReductionEffect(this);
    }
}
