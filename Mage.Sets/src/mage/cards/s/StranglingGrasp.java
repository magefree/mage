package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.CanBeSacrificedPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StranglingGrasp extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public StranglingGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.subtype.add(SubType.AURA);
        this.color.setBlack(true);
        this.nightCard = true;

        // Enchant creature or planeswalker an opponent controls
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of your upkeep, enchanted permanent's controller sacrifices a nonland permanent and loses 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new StranglingGraspEffect()));
    }

    private StranglingGrasp(final StranglingGrasp card) {
        super(card);
    }

    @Override
    public StranglingGrasp copy() {
        return new StranglingGrasp(this);
    }
}

class StranglingGraspEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CanBeSacrificedPredicate.instance);
    }

    StranglingGraspEffect() {
        super(Outcome.Benefit);
        staticText = "enchanted permanent's controller sacrifices a nonland permanent, then that player loses 1 life";
    }

    private StranglingGraspEffect(final StranglingGraspEffect effect) {
        super(effect);
    }

    @Override
    public StranglingGraspEffect copy() {
        return new StranglingGraspEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanentOrLKIBattlefield)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .orElse(null);
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        if (target.canChoose(player.getId(), source, game)) {
            player.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        player.loseLife(1, game, source, false);
        return true;
    }
}
