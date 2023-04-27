package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.GreatestSharedCreatureTypeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SynchronizedEviction extends CardImpl {

    public SynchronizedEviction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // This spell costs {2} less to cast if you control two or more creatures that share a creature type.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, SynchronizedEvictionCondition.instance))
                .addHint(GreatestSharedCreatureTypeCount.getHint()).setRuleAtTheTop(true)
        );

        // Put target nonland permanent into its owner's library second from the top.
        this.getSpellAbility().addEffect(new SynchronizedEvictionEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private SynchronizedEviction(final SynchronizedEviction card) {
        super(card);
    }

    @Override
    public SynchronizedEviction copy() {
        return new SynchronizedEviction(this);
    }
}

enum SynchronizedEvictionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return GreatestSharedCreatureTypeCount.instance.calculate(game, source, null) >= 2;
    }

    @Override
    public String toString() {
        return "you control two or more creatures that share a creature type";
    }
}

class SynchronizedEvictionEffect extends OneShotEffect {

    SynchronizedEvictionEffect() {
        super(Outcome.Benefit);
        staticText = "put target nonland permanent into its owner's library second from the top";
    }

    private SynchronizedEvictionEffect(final SynchronizedEvictionEffect effect) {
        super(effect);
    }

    @Override
    public SynchronizedEvictionEffect copy() {
        return new SynchronizedEvictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return controller != null
                && permanent != null
                && controller.putCardOnTopXOfLibrary(permanent, game, source, 2, true);
    }
}
