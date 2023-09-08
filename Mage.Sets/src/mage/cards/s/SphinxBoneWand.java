package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author North
 */
public final class SphinxBoneWand extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public SphinxBoneWand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        // Whenever you cast an instant or sorcery spell, you may put a charge counter on Sphinx-Bone Wand. If you do, Sphinx-Bone Wand deals damage equal to the number of charge counters on it to any target.
        SpellCastControllerTriggeredAbility ability = new SpellCastControllerTriggeredAbility(new SphinxBoneWandEffect(), filter, true);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SphinxBoneWand(final SphinxBoneWand card) {
        super(card);
    }

    @Override
    public SphinxBoneWand copy() {
        return new SphinxBoneWand(this);
    }
}

class SphinxBoneWandEffect extends OneShotEffect {

    public SphinxBoneWandEffect() {
        super(Outcome.Damage);
        this.staticText = "put a charge counter on Sphinx-Bone Wand. If you do, Sphinx-Bone Wand deals damage equal to the number of charge counters on it to any target";
    }

    private SphinxBoneWandEffect(final SphinxBoneWandEffect effect) {
        super(effect);
    }

    @Override
    public SphinxBoneWandEffect copy() {
        return new SphinxBoneWandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            sourcePermanent.addCounters(CounterType.CHARGE.createInstance(), source.getControllerId(), source, game);
            int amount = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE);

            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                permanent.damage(amount, source.getSourceId(), source, game, false, true);
            }
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                player.damage(amount, source.getSourceId(), source, game);
            }

            return true;
        }
        return false;
    }
}
