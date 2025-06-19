package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MemoryVampire extends CardImpl {

    public MemoryVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Memory Vampire deals combat damage to a player, any number of target players each mill that many cards. Then you may collect evidence 9. When you do, you may cast target nonland card from defending player's graveyard without paying its mana cost.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new MillCardsTargetEffect(SavedDamageValue.MANY)
                        .setText("any number of target players each mill that many cards")
        );
        ability.addEffect(new MemoryVampireEffect());
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);
    }

    private MemoryVampire(final MemoryVampire card) {
        super(card);
    }

    @Override
    public MemoryVampire copy() {
        return new MemoryVampire(this);
    }
}

class MemoryVampireEffect extends OneShotEffect {

    MemoryVampireEffect() {
        super(Outcome.Benefit);
        staticText = "Then you may collect evidence 9. When you do, you may cast target " +
                "nonland card from defending player's graveyard without paying its mana cost";
    }

    private MemoryVampireEffect(final MemoryVampireEffect effect) {
        super(effect);
    }

    @Override
    public MemoryVampireEffect copy() {
        return new MemoryVampireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cost cost = new CollectEvidenceCost(9);
        if (!cost.canPay(source, source, source.getControllerId(), game)
                || !player.chooseUse(outcome, "Collect evidence 9?", source, game)
                || !cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        UUID defenderId = (UUID) getValue("damagedPlayer");
        if (defenderId == null) {
            return true;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST), false
        );
        FilterCard filter = new FilterNonlandCard("nonland card from defending player's graveyard");
        filter.add(new OwnerIdPredicate(defenderId));
        ability.addTarget(new TargetCardInGraveyard(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
