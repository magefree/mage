
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class TormentedHero extends CardImpl {

    public TormentedHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Tormented Hero enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // Heroic - Whenever you cast a spell that targets Tormented Hero, each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new HeroicAbility(new EachOpponentLosesYouGainSumLifeEffect()));
    }

    private TormentedHero(final TormentedHero card) {
        super(card);
    }

    @Override
    public TormentedHero copy() {
        return new TormentedHero(this);
    }
}

class EachOpponentLosesYouGainSumLifeEffect extends OneShotEffect {

    public EachOpponentLosesYouGainSumLifeEffect() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 1 life. You gain life equal to the life lost this way";
    }

    private EachOpponentLosesYouGainSumLifeEffect(final EachOpponentLosesYouGainSumLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lostLife = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            lostLife += game.getPlayer(opponentId).loseLife(1, game, source, false);
        }
        game.getPlayer(source.getControllerId()).gainLife(lostLife, game, source);
        return true;
    }

    @Override
    public EachOpponentLosesYouGainSumLifeEffect copy() {
        return new EachOpponentLosesYouGainSumLifeEffect(this);
    }

}
