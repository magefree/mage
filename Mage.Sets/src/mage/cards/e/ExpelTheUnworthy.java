package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ExpelTheUnworthy extends CardImpl {


    public ExpelTheUnworthy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Kicker {2}{W}
        this.addAbility(new KickerAbility("{2}{W}"));

        // Choose target creature with mana value 3 or less. If this spell was kicked, instead choose target creature. Exile the chosen creature, then its controller gains life equal to its mana value.
        this.getSpellAbility().addEffect(new InfoEffect("Choose target creature with mana value 3 or less. If this spell was kicked, instead choose target creature."));
        this.getSpellAbility().addEffect(new ExileTargetEffect().setText("Exile the chosen creature"));
        this.getSpellAbility().addEffect(new ExpelTheUnworthyEffect());
        this.getSpellAbility().setTargetAdjuster(ExpelTheUnworthyAdjuster.instance);
    }

    private ExpelTheUnworthy(final ExpelTheUnworthy card) {
        super(card);
    }

    @Override
    public ExpelTheUnworthy copy() {
        return new ExpelTheUnworthy(this);
    }
}

enum ExpelTheUnworthyAdjuster implements TargetAdjuster {
    instance;

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        if (KickedCondition.ONCE.apply(game, ability)) {
            ability.addTarget(new TargetCreaturePermanent());
        } else {
            ability.addTarget(new TargetCreaturePermanent(filter));
        }
    }
}


class ExpelTheUnworthyEffect extends OneShotEffect {

    ExpelTheUnworthyEffect() {
        super(Outcome.GainLife);
        staticText = "Its controller gains 4 life";
    }

    private ExpelTheUnworthyEffect(final ExpelTheUnworthyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (target == null) {
            return false;
        }
        Player player = game.getPlayer(target.getControllerId());
        if (player == null) {
            return false;
        }
        return player.gainLife(target.getManaValue(), game, source) > 0;
    }

    @Override
    public ExpelTheUnworthyEffect copy() {
        return new ExpelTheUnworthyEffect(this);
    }
}
