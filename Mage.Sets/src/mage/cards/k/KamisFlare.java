package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KamisFlare extends CardImpl {

    public KamisFlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Kami's Flare deals 3 damage to target creature or planeswalker. Kami's Flare also deals 2 damage to that permanent's controller if you control a modified creature.
        this.getSpellAbility().addEffect(new KamisFlareEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addHint(KamisFlareEffect.getHint());
    }

    private KamisFlare(final KamisFlare card) {
        super(card);
    }

    @Override
    public KamisFlare copy() {
        return new KamisFlare(this);
    }
}

class KamisFlareEffect extends OneShotEffect {
    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(ModifiedPredicate.instance);
    }

    private static final Hint hint = new ConditionHint(
            new PermanentsOnTheBattlefieldCondition(filter), "You control a modified creature"
    );

    KamisFlareEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 3 damage to target creature or planeswalker. " +
                "{this} also deals 2 damage to that permanent's controller if you control a modified creature";
    }

    private KamisFlareEffect(final KamisFlareEffect effect) {
        super(effect);
    }

    @Override
    public KamisFlareEffect copy() {
        return new KamisFlareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.damage(3, source, game);
        if (!game.getBattlefield().contains(filter, source, game, 1)) {
            return true;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            player.damage(2, source, game);
        }
        return true;
    }

    public static Hint getHint() {
        return hint;
    }
}
