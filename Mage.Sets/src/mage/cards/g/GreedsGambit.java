package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Bat21Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreedsGambit extends CardImpl {

    public GreedsGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // When Greed's Gambit enters the battlefield, you draw three cards, gain 6 life, and create three 2/1 black Bat creature tokens with flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(3, "you"));
        ability.addEffect(new GainLifeEffect(6).setText(", gain 6 life"));
        ability.addEffect(new CreateTokenEffect(new Bat21Token(), 3).concatBy(", and"));
        this.addAbility(ability);

        // At the beginning of your end step, you discard a card, lose 2 life, and sacrifice a creature.
        ability = new BeginningOfEndStepTriggeredAbility(new DiscardControllerEffect(1), TargetController.YOU, false);
        ability.addEffect(new LoseLifeSourceControllerEffect(2).setText(", lose 2 life"));
        ability.addEffect(new SacrificeControllerEffect(
                StaticFilters.FILTER_PERMANENT_A_CREATURE, 1, ""
        ).setText(", and sacrifice a creature"));
        this.addAbility(ability);

        // When Greed's Gambit leaves the battlefield, you discard three cards, lose 6 life, and sacrifice three creatures.
        ability = new LeavesBattlefieldTriggeredAbility(new DiscardControllerEffect(3), false);
        ability.addEffect(new LoseLifeSourceControllerEffect(6).setText(", lose 6 life"));
        ability.addEffect(new SacrificeControllerEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, 3, ""
        ).setText(", and sacrifice 3 creatures"));
        this.addAbility(ability);
    }

    private GreedsGambit(final GreedsGambit card) {
        super(card);
    }

    @Override
    public GreedsGambit copy() {
        return new GreedsGambit(this);
    }
}
