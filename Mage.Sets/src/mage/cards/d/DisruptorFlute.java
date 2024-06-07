package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class DisruptorFlute extends CardImpl {

    public DisruptorFlute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");


        // Flash
        this.addAbility(FlashAbility.getInstance());

        // As Disruptor Flute enters the battlefield, choose a card name.
        Effect effect = new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL);
        effect.setText("choose a card name");
        this.addAbility(new AsEntersBattlefieldAbility(effect));

        // Spells with the chosen name cost {3} more to cast.
        this.addAbility(new SimpleStaticAbility(new DisruptorFluteCostIncreaseEffect()));

        // Activated abilities of sources with the chosen name can't be activated unless they're mana abilities.
        this.addAbility(new SimpleStaticAbility(new DisruptorFluteCostEffect()));
    }

    private DisruptorFlute(final DisruptorFlute card) {
        super(card);
    }

    @Override
    public DisruptorFlute copy() {
        return new DisruptorFlute(this);
    }
}

class DisruptorFluteCostIncreaseEffect extends CostModificationEffectImpl {

    DisruptorFluteCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.staticText = "Spells with the chosen name cost {3} more to cast";
    }

    private DisruptorFluteCostIncreaseEffect(DisruptorFluteCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public DisruptorFluteCostIncreaseEffect copy() {
        return new DisruptorFluteCostIncreaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 3);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility)) {
            Card card = game.getCard(abilityToModify.getSourceId());
            if (card != null) {
                String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
                return CardUtil.haveSameNames(card, cardName, game);
            }
        }
        return false;
    }
}

class DisruptorFluteCostEffect extends ContinuousRuleModifyingEffectImpl {

    DisruptorFluteCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Activated abilities of sources with the chosen name can't be activated unless they're mana abilities";
    }

    private DisruptorFluteCostEffect(final DisruptorFluteCostEffect effect) {
        super(effect);
    }

    @Override
    public DisruptorFluteCostEffect copy() {
        return new DisruptorFluteCostEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
        if (ability.isPresent() && object != null) {
            return game.getState().getPlayersInRange(source.getControllerId(), game).contains(event.getPlayerId()) // controller in range
                    && !ability.get().isManaAbility()
                    && CardUtil.haveSameNames(object, cardName, game);
        }
        return false;
    }
}
