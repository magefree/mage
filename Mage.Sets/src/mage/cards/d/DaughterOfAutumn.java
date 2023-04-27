
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DaughterOfAutumn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public DaughterOfAutumn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {W}: The next 1 damage that would be dealt to target white creature this turn is dealt to Daughter of Autumn instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DaughterOfAutumnPreventDamageTargetEffect(Duration.EndOfTurn, 1), new ManaCostsImpl<>("{W}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private DaughterOfAutumn(final DaughterOfAutumn card) {
        super(card);
    }

    @Override
    public DaughterOfAutumn copy() {
        return new DaughterOfAutumn(this);
    }
}

class DaughterOfAutumnPreventDamageTargetEffect extends RedirectionEffect {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public DaughterOfAutumnPreventDamageTargetEffect(Duration duration, int amount) {
        super(duration, amount, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next " + amount + " damage that would be dealt to target white creature this turn is dealt to {this} instead";
    }

    public DaughterOfAutumnPreventDamageTargetEffect(final DaughterOfAutumnPreventDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public DaughterOfAutumnPreventDamageTargetEffect copy() {
        return new DaughterOfAutumnPreventDamageTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            if (filter.match(permanent, permanent.getControllerId(), source, game)) {
                if (event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
                    if (event.getTargetId() != null) {
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
