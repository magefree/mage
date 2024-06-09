package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.RatCantBlockToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TwistedSewerWitch extends CardImpl {

    public TwistedSewerWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Twisted Sewer-Witch enters the battlefield, create a 1/1 black Rat creature token with "This creature can't block." Then for each Rat you control, create a Wicked Role token attached to that Rat.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RatCantBlockToken()));
        ability.addEffect(new TwistedSewerWitchEffect());
        this.addAbility(ability);
    }

    private TwistedSewerWitch(final TwistedSewerWitch card) {
        super(card);
    }

    @Override
    public TwistedSewerWitch copy() {
        return new TwistedSewerWitch(this);
    }
}

class TwistedSewerWitchEffect extends OneShotEffect {

    // Creature has been added since Roles can only attach to creatures. No need to look for Tribal Rats.
    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent(SubType.RAT, "Rat you control");

    TwistedSewerWitchEffect() {
        super(Outcome.Benefit);
        staticText = "Then for each Rat you control, create a Wicked Role token attached to that Rat.";
    }

    private TwistedSewerWitchEffect(final TwistedSewerWitchEffect effect) {
        super(effect);
    }

    @Override
    public TwistedSewerWitchEffect copy() {
        return new TwistedSewerWitchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            RoleType.WICKED.createToken(permanent, game, source);
        }
        return true;
    }
}
