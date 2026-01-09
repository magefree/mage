package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.common.BeholdAndExileCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnExiledCardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.BeholdType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChampionOfThePath extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ELEMENTAL, "another Elemental you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ChampionOfThePath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(3);

        // As an additional cost to cast this spell, behold an Elemental and exile it.
        this.getSpellAbility().addCost(new BeholdAndExileCost(BeholdType.ELEMENTAL));

        // Whenever another Elemental you control enters, it deals damage equal to its power to each opponent.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new ChampionOfThePathEffect(), filter));

        // When this creature leaves the battlefield, return the exiled card to its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnExiledCardToHandEffect()));
    }

    private ChampionOfThePath(final ChampionOfThePath card) {
        super(card);
    }

    @Override
    public ChampionOfThePath copy() {
        return new ChampionOfThePath(this);
    }
}

class ChampionOfThePathEffect extends OneShotEffect {

    ChampionOfThePathEffect() {
        super(Outcome.Benefit);
        staticText = "it deals damage equal to its power to each opponent";
    }

    private ChampionOfThePathEffect(final ChampionOfThePathEffect effect) {
        super(effect);
    }

    @Override
    public ChampionOfThePathEffect copy() {
        return new ChampionOfThePathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        if (power < 1) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Optional.of(opponentId)
                    .map(game::getPlayer)
                    .ifPresent(player -> player.damage(power, permanent.getId(), source, game));
        }
        return true;
    }
}
