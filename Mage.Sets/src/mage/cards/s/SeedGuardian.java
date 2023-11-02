package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SeedGuardianToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SeedGuardian extends CardImpl {

    public SeedGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // When Seed Guardian dies, create an X/X green Elemental creature token, where X is the number of creature cards in your graveyard.
        this.addAbility(new DiesSourceTriggeredAbility(new SeedGuardianEffect(), false));
    }

    private SeedGuardian(final SeedGuardian card) {
        super(card);
    }

    @Override
    public SeedGuardian copy() {
        return new SeedGuardian(this);
    }
}

class SeedGuardianEffect extends OneShotEffect {

    public SeedGuardianEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create an X/X green Elemental creature token, where X is the number of creature cards in your graveyard";
    }

    private SeedGuardianEffect(final SeedGuardianEffect effect) {
        super(effect);
    }

    @Override
    public SeedGuardianEffect copy() {
        return new SeedGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int creaturesInGraveyard = controller.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
            return new CreateTokenEffect(new SeedGuardianToken(creaturesInGraveyard)).apply(game, source);
        }
        return false;
    }
}
