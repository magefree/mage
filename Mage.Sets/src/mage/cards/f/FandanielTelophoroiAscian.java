package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author balazskristof
 */
public final class FandanielTelophoroiAscian extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);
    private static final Hint hint = new ValueHint("Instant and sorcery cards in your graveyard", xValue);

    public FandanielTelophoroiAscian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever you cast an instant or sorcery spell, surveil 1.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new SurveilEffect(1), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    
        // At the beginning of your end step, each opponent may sacrifice a nontoken creature of their choice. Each opponent who doesn't loses 2 life for each instant and sorcery card in your graveyard.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new FandanielTelophoroiAscianEffect(xValue)).addHint(hint));
    }

    private FandanielTelophoroiAscian(final FandanielTelophoroiAscian card) {
        super(card);
    }

    @Override
    public FandanielTelophoroiAscian copy() {
        return new FandanielTelophoroiAscian(this);
    }
}

class FandanielTelophoroiAscianEffect extends OneShotEffect {

    protected final DynamicValue amount;

    public FandanielTelophoroiAscianEffect(DynamicValue amount) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.staticText = "each opponent may sacrifice a nontoken creature of their choice. "
                + "Each opponent who doesn't loses 2 life for each instant and sorcery card in your graveyard.";
    }

    private FandanielTelophoroiAscianEffect(final FandanielTelophoroiAscianEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public FandanielTelophoroiAscianEffect copy() {
        return new FandanielTelophoroiAscianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 2 * amount.calculate(game, source, this);
        if (damage <= 0) {
            return true;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId(), true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            Cost cost = new SacrificeTargetCost(1, StaticFilters.FILTER_CREATURE_NON_TOKEN);
            if (!cost.canPay(source, source, opponentId, game)
                    || !opponent.chooseUse(Outcome.Detriment, "Sacrifice a nontoken creature to avoid " + damage + " damage?", source, game)
                    || !cost.pay(source, game, source, opponentId, false)) {
                opponent.loseLife(damage, game, source, false);
            }
        }
        return true;
    }
}
