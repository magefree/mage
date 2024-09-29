package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SpecterOfMortality extends CardImpl {

    public SpecterOfMortality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Specter of Mortality enters the battlefield, you may exile one or more creature cards from your graveyard. When you do, each other creature gets -X/-X until end of turn, where X is the number of cards exiled this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoWhenCostPaid(
                        new ReflexiveTriggeredAbility(new SpecterOfMortalityEffect(), false),
                        new ExileFromGraveCost(
                                new TargetCardInYourGraveyard(
                                        1, Integer.MAX_VALUE,
                                        StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD, true
                                ), true
                        ),
                        "Exile creature cards from your graveyard?"
                )
        ));
    }

    private SpecterOfMortality(final SpecterOfMortality card) {
        super(card);
    }

    @Override
    public SpecterOfMortality copy() {
        return new SpecterOfMortality(this);
    }
}

class SpecterOfMortalityEffect extends OneShotEffect {

    SpecterOfMortalityEffect() {
        super(Outcome.Removal);
        staticText = "each other creature gets -X/-X until end of turn, where X is the number of cards exiled this way";
    }

    private SpecterOfMortalityEffect(final SpecterOfMortalityEffect effect) {
        super(effect);
    }

    @Override
    public SpecterOfMortalityEffect copy() {
        return new SpecterOfMortalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = getTargetPointer().getTargets(game, source).size();
        if (amount == 0) {
            return false;
        }

        game.addEffect(new BoostAllEffect(
                -amount, -amount, Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ), source);

        return true;
    }

}
