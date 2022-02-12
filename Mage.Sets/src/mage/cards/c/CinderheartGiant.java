package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class CinderheartGiant extends CardImpl {

    public CinderheartGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Cinderheart Giant dies, it deals 7 damage to a creature an opponent controls chosen at random.
        this.addAbility(new DiesSourceTriggeredAbility(new CinderheartGiantEffect()));
    }

    private CinderheartGiant(final CinderheartGiant card) {
        super(card);
    }

    @Override
    public CinderheartGiant copy() {
        return new CinderheartGiant(this);
    }
}

class CinderheartGiantEffect extends OneShotEffect {

    CinderheartGiantEffect() {
        super(Outcome.Benefit);
        staticText = "it deals 7 damage to a creature an opponent controls chosen at random";
    }

    private CinderheartGiantEffect(final CinderheartGiantEffect effect) {
        super(effect);
    }

    @Override
    public CinderheartGiantEffect copy() {
        return new CinderheartGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || game.getBattlefield().count(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE,
                source.getSourceId(), source.getControllerId(), game
        ) < 1) {
            return false;
        }
        TargetPermanent target = new TargetOpponentsCreaturePermanent();
        target.setNotTarget(true);
        target.setRandom(true);
        target.chooseTarget(outcome, player.getId(), source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.damage(7, source.getSourceId(), source, game) > 0;
    }
}
