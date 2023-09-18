package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AssimilateEssence extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or battle spell");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    public AssimilateEssence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target creature or battle spell unless its controller pays {4}. If they do, you incubate 2.
        this.getSpellAbility().addEffect(new AssimilateEssenceEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private AssimilateEssence(final AssimilateEssence card) {
        super(card);
    }

    @Override
    public AssimilateEssence copy() {
        return new AssimilateEssence(this);
    }
}

class AssimilateEssenceEffect extends OneShotEffect {

    AssimilateEssenceEffect() {
        super(Outcome.Benefit);
        staticText = "counter target creature or battle spell unless its controller pays {4}. If they do, you incubate 2";
    }

    private AssimilateEssenceEffect(final AssimilateEssenceEffect effect) {
        super(effect);
    }

    @Override
    public AssimilateEssenceEffect copy() {
        return new AssimilateEssenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        Player player = game.getPlayer(game.getControllerId(targetId));
        Cost cost = new GenericManaCost(4);
        if (player == null
                || !cost.canPay(source, source, player.getId(), game)
                || !player.chooseUse(outcome, "Pay {4}?", source, game)
                || !cost.pay(source, game, source, player.getId(), false)) {
            game.getStack().counter(targetId, source, game);
            return true;
        }
        return IncubateEffect.doIncubate(2, source.getControllerId(), game, source);
    }
}
