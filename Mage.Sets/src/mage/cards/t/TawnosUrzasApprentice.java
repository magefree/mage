package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.ArtifactSourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.common.TargetActivatedOrTriggeredAbility;

/**
 *
 * @author TheElk801
 */
public final class TawnosUrzasApprentice extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("activated or triggered ability you control from an artifact source");

    static {
        filter.add(ArtifactSourcePredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public TawnosUrzasApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {U}{R}, {T}: Copy target activated or triggered ability you control from an artifact source. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TawnosUrzasApprenticeEffect(), new ManaCostsImpl<>("{U}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetActivatedOrTriggeredAbility(filter));
        this.addAbility(ability);
    }

    private TawnosUrzasApprentice(final TawnosUrzasApprentice card) {
        super(card);
    }

    @Override
    public TawnosUrzasApprentice copy() {
        return new TawnosUrzasApprentice(this);
    }
}

class TawnosUrzasApprenticeEffect extends OneShotEffect {

    public TawnosUrzasApprenticeEffect() {
        super(Outcome.Copy);
        this.staticText = "copy target activated or triggered ability you control from an artifact source. You may choose new targets for the copy";
    }

    public TawnosUrzasApprenticeEffect(final TawnosUrzasApprenticeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackAbility != null) {
            Player controller = game.getPlayer(source.getControllerId());
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (controller != null && sourcePermanent != null) {
                stackAbility.createCopyOnStack(game, source, source.getControllerId(), true);
                return true;
            }
        }
        return false;

    }

    @Override
    public TawnosUrzasApprenticeEffect copy() {
        return new TawnosUrzasApprenticeEffect(this);
    }
}
