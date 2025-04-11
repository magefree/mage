package mage.cards.o;

import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.Ability;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.OneShotEffect;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
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
        this.staticText = "each opponent may sacrifice a nonland permanent or discard a card. Then {this} "
                       + "deals damage equal to its power to each opponent who didn't sacrifice a permanent or discard a card in this way.";
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

        Set<UUID> opponentsIds = game.getOpponents(source.getControllerId());

        HashMap<UUID, MageInt> damageMap = new HashMap<UUID, MageInt>();
        for (UUID id : game.getState().getPlayerList(source.getControllerId())) {
            if (!opponentsIds.contains(id)) {
                continue;
            }

            Player opponent = game.getPlayer(id);
            if (opponent == null) {
                continue;
            }

            Cost cost = new OrCost(
                "sacrifice a nonland permanent or discard a card",
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_NON_LAND),
                new DiscardCardCost()
            );

            // Damage equal to creature power.
            MageInt dmg = game.getPermanent(source.getSourceId()).getPower();
            boolean canPay = cost.canPay(source, source, id, game);

            if (canPay) {
                // Face the choice.
                boolean choice = opponent.chooseUse(
                    Outcome.Detriment, 
                    "Sacrifice a nonland permanent or discard a card to avoid " + dmg.getValue() + " damage?",
                    source, game);
                
                if (choice) { // Pay Cost
                    cost.pay(source, game, source, id, false);
                    continue; // Paid skip damage
                }
            }
            // They either can't discard or sacrifice or chose not to.
            damageMap.put(id, dmg);
        } // End for

        // Do the damage to the opponents that chose not to pay the cost.
        damageMap.forEach((id, dmg) -> game.getPlayer(id).damage(dmg.getValue(), source.getSourceId(), source, game));

        return true;
    }
}