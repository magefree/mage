package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureRandomCreatureEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

public final class FearOfChange extends CardImpl {

    public FearOfChange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{G}{U}");
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When this creature enters or dies, exile another creature you control. If you do, conjure a duplicate of a random creature card with mana value X onto the battlefield, where X is 2 plus the exiled creature's mana value.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new FearOfChangeEffect(), false));
    }

    private FearOfChange(final FearOfChange card) {
        super(card);
    }

    @Override
    public FearOfChange copy() {
        return new FearOfChange(this);
    }
}

class FearOfChangeEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    FearOfChangeEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "exile another creature you control. If you do, conjure a duplicate of a random creature card "
                + "with mana value X onto the battlefield, where X is 2 plus the exiled creature's mana value";
    }

    private FearOfChangeEffect(final FearOfChangeEffect effect) {
        super(effect);
    }

    @Override
    public FearOfChangeEffect copy() {
        return new FearOfChangeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
        if (controller == null || !controller.choose(outcome, target, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int manaValue = permanent.getManaValue() + 2;
        if (controller.moveCards(permanent, Zone.EXILED, source, game)) {
            ConjureRandomCreatureEffect.conjure(manaValue, Zone.BATTLEFIELD, game, source);
        }
        return true;
    }
}
