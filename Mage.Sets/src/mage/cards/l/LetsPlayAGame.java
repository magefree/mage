package mage.cards.l;

import mage.abilities.Mode;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LetsPlayAGame extends CardImpl {

    public LetsPlayAGame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Delirium -- Choose one. If there are four or more card types among cards in your graveyard, choose one or more instead.
        this.getSpellAbility().getModes().setChooseText(
                "choose one. If there are four or more card types among cards in your graveyard, choose one or more instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(3, DeliriumCondition.instance);
        this.getSpellAbility().setAbilityWord(AbilityWord.DELIRIUM);
        this.getSpellAbility().addHint(CardTypesInGraveyardCount.YOU.getHint());

        // * Creatures your opponents control get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false
        ));

        // * Each opponent discards two cards.
        this.getSpellAbility().addMode(new Mode(new DiscardEachPlayerEffect(
                StaticValue.get(2), false, TargetController.OPPONENT
        )));

        // * Each opponent loses 3 life and you gain 3 life.
        this.getSpellAbility().addMode(new Mode(new LoseLifeOpponentsEffect(3))
                .addEffect(new GainLifeEffect(3).concatBy("and")));
    }

    private LetsPlayAGame(final LetsPlayAGame card) {
        super(card);
    }

    @Override
    public LetsPlayAGame copy() {
        return new LetsPlayAGame(this);
    }
}
