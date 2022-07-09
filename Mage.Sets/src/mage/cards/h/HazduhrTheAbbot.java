
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
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
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801 & L_J
 */
public final class HazduhrTheAbbot extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("white creature you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public HazduhrTheAbbot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // {X}, {T}: The next X damage that would be dealt this turn to target white creature you control is dealt to Hazduhr the Abbot instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HazduhrTheAbbotRedirectDamageEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private HazduhrTheAbbot(final HazduhrTheAbbot card) {
        super(card);
    }

    @Override
    public HazduhrTheAbbot copy() {
        return new HazduhrTheAbbot(this);
    }
}

class HazduhrTheAbbotRedirectDamageEffect extends RedirectionEffect {

    private static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    public HazduhrTheAbbotRedirectDamageEffect(Duration duration) {
        super(duration, 0, UsageType.ACCORDING_DURATION);
        this.staticText = "The next X damage that would be dealt this turn to target white creature you control is dealt to {this} instead";
    }

    public HazduhrTheAbbotRedirectDamageEffect(final HazduhrTheAbbotRedirectDamageEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        amountToRedirect = source.getManaCostsToPay().getX();
    }

    @Override
    public HazduhrTheAbbotRedirectDamageEffect copy() {
        return new HazduhrTheAbbotRedirectDamageEffect(this);
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
