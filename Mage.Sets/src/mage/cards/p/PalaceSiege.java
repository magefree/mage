package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class PalaceSiege extends CardImpl {

    private static final String ruleTrigger1 = "&bull  Khans &mdash; At the beginning of your upkeep, return target creature card from your graveyard to your hand.";
    private static final String ruleTrigger2 = "&bull  Dragons &mdash; At the beginning of your upkeep, each opponent loses 2 life and you gain 2 life.";

    public PalaceSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // As Palace Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Khans or Dragons?", "Khans", "Dragons"), null,
                "As {this} enters the battlefield, choose Khans or Dragons.", ""));

        // * Khans - At the beginning of your upkeep, return target creature card from your graveyard to your hand.
        Ability ability1 = new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), TargetController.YOU, false),
                new ModeChoiceSourceCondition("Khans"),
                ruleTrigger1);
        ability1.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability1);

        // * Dragons - At the beginning of your upkeep, each opponent loses 2 life and you gain 2 life.
        Ability ability2 = new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new LoseLifeOpponentsEffect(2), TargetController.YOU, false),
                new ModeChoiceSourceCondition("Dragons"),
                ruleTrigger2);
        Effect effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        ability2.addEffect(effect);
        this.addAbility(ability2);

    }

    private PalaceSiege(final PalaceSiege card) {
        super(card);
    }

    @Override
    public PalaceSiege copy() {
        return new PalaceSiege(this);
    }
}
