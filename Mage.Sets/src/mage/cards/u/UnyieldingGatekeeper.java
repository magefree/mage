package mage.cards.u;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenControllerTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DetectiveToken;
import mage.target.common.TargetNonlandPermanent;

/**
 * @author Cguy7777
 */
public final class UnyieldingGatekeeper extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public UnyieldingGatekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Disguise {1}{W}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{1}{W}")));

        // When Unyielding Gatekeeper is turned face up, exile another target nonland permanent.
        // If you controlled it, return it to the battlefield tapped.
        // Otherwise, its controller creates a 2/2 white and blue Detective creature token.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new UnyieldingGatekeeperEffect());
        ability.addTarget(new TargetNonlandPermanent(filter));
        this.addAbility(ability);
    }

    private UnyieldingGatekeeper(final UnyieldingGatekeeper card) {
        super(card);
    }

    @Override
    public UnyieldingGatekeeper copy() {
        return new UnyieldingGatekeeper(this);
    }
}

class UnyieldingGatekeeperEffect extends OneShotEffect {

    UnyieldingGatekeeperEffect() {
        super(Outcome.Benefit);
        staticText = "exile another target nonland permanent. " +
                "If you controlled it, return it to the battlefield tapped. " +
                "Otherwise, its controller creates a 2/2 white and blue Detective creature token";
    }

    private UnyieldingGatekeeperEffect(final UnyieldingGatekeeperEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }

        if (permanent.isControlledBy(source.getControllerId())) {
            new ExileThenReturnTargetEffect(
                    false, false, PutCards.BATTLEFIELD_TAPPED).apply(game, source);
        } else {
            new ExileTargetEffect().apply(game, source);
            new CreateTokenControllerTargetEffect(new DetectiveToken()).apply(game, source);
        }
        return true;
    }

    @Override
    public UnyieldingGatekeeperEffect copy() {
        return new UnyieldingGatekeeperEffect(this);
    }
}
