package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BuryInBooks extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("an attacking creature");
    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public BuryInBooks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // This spell costs {2} less to cast if it targets an attacking creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Put target creature into its owner's library second from the top.
        this.getSpellAbility().addEffect(new BuryInBooksEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BuryInBooks(final BuryInBooks card) {
        super(card);
    }

    @Override
    public BuryInBooks copy() {
        return new BuryInBooks(this);
    }
}

class BuryInBooksEffect extends OneShotEffect {

    BuryInBooksEffect() {
        super(Outcome.Benefit);
        staticText = "put target creature into its owner's library second from the top";
    }

    private BuryInBooksEffect(final BuryInBooksEffect effect) {
        super(effect);
    }

    @Override
    public BuryInBooksEffect copy() {
        return new BuryInBooksEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        return player != null
                && permanent != null
                && player.putCardOnTopXOfLibrary(permanent, game, source, 2, true);
    }
}
