package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class GraveVenerations extends CardImpl {

    public GraveVenerations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // When this enchantment enters, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of your end step, if you're the monarch, return up to one target creature card from your graveyard to your hand.
        Ability monarchAbility = new BeginningOfEndStepTriggeredAbility(
            TargetController.YOU,
            new ReturnFromGraveyardToHandTargetEffect(),
            false,
            MonarchIsSourceControllerCondition.instance
        );
        monarchAbility.addTarget(new TargetCardInYourGraveyard(
            0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        ));
        this.addAbility(monarchAbility);

        // Whenever a creature you control dies, each opponent loses 1 life and you gain 1 life.
        Ability diesAbility = new DiesCreatureTriggeredAbility(
            new LoseLifeOpponentsEffect(1), false,
            StaticFilters.FILTER_CONTROLLED_A_CREATURE
        );
        diesAbility.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(diesAbility);
    }

    private GraveVenerations(final GraveVenerations card) {
        super(card);
    }

    @Override
    public GraveVenerations copy() {
        return new GraveVenerations(this);
    }
}
