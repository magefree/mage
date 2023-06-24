

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;

/**
 * @author Loki
 */
public final class KokushoTheEveningStar extends CardImpl {

    public KokushoTheEveningStar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new DiesSourceTriggeredAbility(new KokushoTheEveningStarEffect(), false));
    }

    private KokushoTheEveningStar(final KokushoTheEveningStar card) {
        super(card);
    }

    @Override
    public KokushoTheEveningStar copy() {
        return new KokushoTheEveningStar(this);
    }

}

class KokushoTheEveningStarEffect extends OneShotEffect {
    public KokushoTheEveningStarEffect() {
        super(Outcome.Damage);
        staticText = "each opponent loses 5 life. You gain life equal to the life lost this way";
    }

    public KokushoTheEveningStarEffect(final KokushoTheEveningStarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int loseLife = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            loseLife += game.getPlayer(opponentId).loseLife(5, game, source, false);
        }
        if (loseLife > 0)
            game.getPlayer(source.getControllerId()).gainLife(loseLife, game, source);
        return true;
    }

    @Override
    public KokushoTheEveningStarEffect copy() {
        return new KokushoTheEveningStarEffect(this);
    }

}