package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class BrambleSovereign extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
    }

    public BrambleSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever another nontoken creature enters the battlefield, you may pay {1}{G}. If you do, that creature's controller creates a token that's a copy of that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new DoIfCostPaid(new BrambleSovereignEffect(), new ManaCostsImpl<>("{1}{G}")),
                filter, false, SetTargetPointer.PERMANENT,
                "Whenever another nontoken creature enters the battlefield, you may pay {1}{G}. "
                + "If you do, that creature's controller creates a token that's a copy of that creature."
        ));
    }

    private BrambleSovereign(final BrambleSovereign card) {
        super(card);
    }

    @Override
    public BrambleSovereign copy() {
        return new BrambleSovereign(this);
    }
}

class BrambleSovereignEffect extends OneShotEffect {

    BrambleSovereignEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "its controller creates a token that's a copy of that creature";
    }

    BrambleSovereignEffect(final BrambleSovereignEffect effect) {
        super(effect);
    }

    @Override
    public BrambleSovereignEffect copy() {
        return new BrambleSovereignEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(permanent.getControllerId());
            effect.setTargetPointer(targetPointer);
            effect.apply(game, source);
        }
        return true;
    }
}
