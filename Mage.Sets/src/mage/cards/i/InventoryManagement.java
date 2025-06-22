package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class InventoryManagement extends CardImpl {

    public InventoryManagement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");

        // Split second
        this.addAbility(new SplitSecondAbility());

        // For each Aura and Equipment you control, you may attach it to a creature you control.
        this.getSpellAbility().addEffect(new InventoryManagementEffect());
    }

    private InventoryManagement(final InventoryManagement card) {
        super(card);
    }

    @Override
    public InventoryManagement copy() {
        return new InventoryManagement(this);
    }
}

class InventoryManagementEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("Aura or Equipment you control");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    InventoryManagementEffect() {
        super(Outcome.Benefit);
        staticText = "for each Aura and Equipment you control, you may attach it to a creature you control";
    }

    private InventoryManagementEffect(final InventoryManagementEffect effect) {
        super(effect);
    }

    @Override
    public InventoryManagementEffect copy() {
        return new InventoryManagementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null
                || !game.getBattlefield().contains(filter, source, game, 1)
                || !game.getBattlefield().contains(StaticFilters.FILTER_CONTROLLED_CREATURE, source, game, 1)) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        target.withChooseHint("to attach to a creature you control");
        player.choose(outcome, target, source, game);
        List<Permanent> permanents = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent permanent : permanents) {
            TargetPermanent targetCreature = new TargetControlledCreaturePermanent(0, 1);
            targetCreature.withNotTarget(true);
            targetCreature.withChooseHint("to attach " + permanent.getLogName() + " to");
            Optional.ofNullable(targetCreature)
                    .map(TargetImpl::getFirstTarget)
                    .map(game::getPermanent)
                    .ifPresent(p -> p.addAttachment(permanent.getId(), source, game));
        }
        return true;
    }
}
