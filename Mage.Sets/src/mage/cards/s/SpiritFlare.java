package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.TimingRule;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiritFlare extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("untapped creature you control");
    private static final FilterPermanent filter2
            = new FilterAttackingOrBlockingCreature("attacking or blocking creature an opponent controls");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SpiritFlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Tap target untapped creature you control. If you do, it deals damage equal to its power to target attacking or blocking creature an opponent controls.
        this.getSpellAbility().addEffect(new SpiritFlareEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addTarget(new TargetPermanent(filter2));

        // Flashback-{1}{W}, Pay 3 life.
        FlashbackAbility ability = new FlashbackAbility(this, new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new PayLifeCost(3));
        this.addAbility(ability);
    }

    private SpiritFlare(final SpiritFlare card) {
        super(card);
    }

    @Override
    public SpiritFlare copy() {
        return new SpiritFlare(this);
    }
}

class SpiritFlareEffect extends OneShotEffect {

    SpiritFlareEffect() {
        super(Outcome.Benefit);
        staticText = "tap target untapped creature you control. If you do, " +
                "it deals damage equal to its power to target attacking or blocking creature an opponent controls";
    }

    private SpiritFlareEffect(final SpiritFlareEffect effect) {
        super(effect);
    }

    @Override
    public SpiritFlareEffect copy() {
        return new SpiritFlareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null || !permanent.tap(source, game)) {
            return false;
        }
        Permanent permanent1 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent1 == null) {
            return false;
        }
        return permanent1.damage(permanent.getPower().getValue(), permanent.getId(), source, game) > 0;
    }
}
