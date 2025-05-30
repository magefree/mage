package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.CreaturesYouControlDiedCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author jimga150
 */
public final class SeasonOfLoss extends CardImpl {
    public SeasonOfLoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMaxPawPrints(5);
        this.getSpellAbility().getModes().setMinModes(0);
        this.getSpellAbility().getModes().setMaxModes(5);
        this.getSpellAbility().getModes().setMayChooseSameModeMoreThanOnce(true);

        // {P} -- Each player sacrifices a creature.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(1, StaticFilters.FILTER_PERMANENT_CREATURE));
        this.getSpellAbility().getModes().getMode().withPawPrintValue(1);

        // {P}{P} -- Draw a card for each creature you controlled that died this turn.
        Mode mode2 = new Mode(new DrawCardSourceControllerEffect(CreaturesYouControlDiedCount.instance));
        this.getSpellAbility().addMode(mode2.withPawPrintValue(2));

        // {P}{P}{P} -- Each opponent loses X life, where X is the number of creature cards in your graveyard.
        DynamicValue creatureCardsInGraveyard = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES);
        Mode mode3 = new Mode(new LoseLifeOpponentsEffect(creatureCardsInGraveyard)
                .setText("Each opponent loses X life, where X is the number of creature cards in your graveyard."));
        this.getSpellAbility().addMode(mode3.withPawPrintValue(3));
        this.getSpellAbility().addHint(new ValueHint("Creature cards in your graveyard", creatureCardsInGraveyard));
    }

    private SeasonOfLoss(final SeasonOfLoss card) {
        super(card);
    }

    @Override
    public SeasonOfLoss copy() {
        return new SeasonOfLoss(this);
    }
}