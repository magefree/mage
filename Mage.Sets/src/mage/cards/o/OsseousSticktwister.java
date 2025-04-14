package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author earchip94
 */
public final class OsseousSticktwister extends CardImpl {

    public OsseousSticktwister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Delirium -- At the beginning of your end step, if there are four or more card types among cards in your graveyard, each opponent may sacrifice a nonland permanent or discard a card. Then Osseous Sticktwister deals damage equal to its power to each opponent who didn't sacrifice a permanent or discard a card this way.
        Ability deliriumAbility = new BeginningOfEndStepTriggeredAbility(TargetController.YOU, new OsseousSticktwisterEffect(), false, DeliriumCondition.instance);
        deliriumAbility.setAbilityWord(AbilityWord.DELIRIUM);
        deliriumAbility.addHint(CardTypesInGraveyardCount.YOU.getHint());
        this.addAbility(deliriumAbility);
    }

    private OsseousSticktwister(final OsseousSticktwister card) {
        super(card);
    }

    @Override
    public OsseousSticktwister copy() {
        return new OsseousSticktwister(this);
    }
}

class OsseousSticktwisterEffect extends OneShotEffect {

    OsseousSticktwisterEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent may sacrifice a nonland permanent of their choice or discard a card. Then {this} "
                + "deals damage equal to its power to each opponent who didn't sacrifice a permanent or discard a card this way.";
    }

    OsseousSticktwisterEffect(final OsseousSticktwisterEffect effect) {
        super(effect);
    }

    @Override
    public OsseousSticktwisterEffect copy() {
        return new OsseousSticktwisterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        int dmgAmt = permanent == null ? 0 : permanent.getPower().getValue();
        List<UUID> playersToDamage = new ArrayList<>();
        String message = "Sacrifice a nonland permanent or discard a card to avoid " + dmgAmt + " damage?";
        for (UUID uuid : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(uuid);
            if (opponent == null) {
                continue;
            }
            Cost cost = new OrCost(
                    "sacrifice a nonland permanent or discard a card",
                    new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_NON_LAND),
                    new DiscardCardCost()
            );
            if (!cost.canPay(source, source, opponent.getId(), game)
                    || !opponent.chooseUse(Outcome.Detriment, message, source, game)
                    || !cost.pay(source, game, source, opponent.getId(), false)) {
                playersToDamage.add(opponent.getId());
            }
        }
        if (dmgAmt <= 0) {
            return true;
        }
        game.processAction();
        for (UUID uuid : playersToDamage) {
            Player opponent = game.getPlayer(uuid);
            if (opponent != null) {
                opponent.damage(dmgAmt, source, game);
            }
        }
        return true;
    }
}
