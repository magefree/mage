package mage.cards.p;

import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PopularEntertainer extends CardImpl {

    public PopularEntertainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever one or more creatures you control deal combat damage to a player, goad target creature that player controls."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new PopularEntertainerAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private PopularEntertainer(final PopularEntertainer card) {
        super(card);
    }

    @Override
    public PopularEntertainer copy() {
        return new PopularEntertainer(this);
    }
}

class PopularEntertainerAbility extends DealCombatDamageControlledTriggeredAbility {

    PopularEntertainerAbility() {
        super(new GoadTargetEffect());
    }

    private PopularEntertainerAbility(final PopularEntertainerAbility ability) {
        super(ability);
    }

    @Override
    public PopularEntertainerAbility copy() {
        return new PopularEntertainerAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Player player = game.getPlayer(event.getTargetId());
        if (player == null) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent(
                "creature controlled by " + player.getName()
        );
        filter.add(new ControllerIdPredicate(player.getId()));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control deal combat damage " +
                "to a player, goad target creature that player controls.";
    }
}
