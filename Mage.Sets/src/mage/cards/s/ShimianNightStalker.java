
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801 & L_J
 */
public final class ShimianNightStalker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creature");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public ShimianNightStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {B}, {T}: All damage that would be dealt to you this turn by target attacking creature is dealt to Shimian Night Stalker instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShimianNightStalkerRedirectDamageEffect(), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private ShimianNightStalker(final ShimianNightStalker card) {
        super(card);
    }

    @Override
    public ShimianNightStalker copy() {
        return new ShimianNightStalker(this);
    }
}

class ShimianNightStalkerRedirectDamageEffect extends RedirectionEffect {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public ShimianNightStalkerRedirectDamageEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, UsageType.ONE_USAGE_ABSOLUTE);
        this.staticText = "All damage that would be dealt to you this turn by target attacking creature is dealt to {this} instead";
    }

    public ShimianNightStalkerRedirectDamageEffect(final ShimianNightStalkerRedirectDamageEffect effect) {
        super(effect);
    }

    @Override
    public ShimianNightStalkerRedirectDamageEffect copy() {
        return new ShimianNightStalkerRedirectDamageEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            if (filter.match(permanent, permanent.getControllerId(), source, game)) {
                if (event.getSourceId() != null && event.getTargetId() != null) {
                    if (event.getSourceId().equals(getTargetPointer().getFirst(game, source))
                            && event.getTargetId().equals(source.getControllerId())) {
                        TargetPermanent target = new TargetPermanent();
                        target.add(source.getSourceId(), game);
                        redirectTarget = target;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
