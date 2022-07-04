package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ScholarOfAthreos extends CardImpl {

    public ScholarOfAthreos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {2}{B}: Each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScholarOfAthreosEffect(), new ManaCostsImpl<>("{2}{B}")));
    }

    private ScholarOfAthreos(final ScholarOfAthreos card) {
        super(card);
    }

    @Override
    public ScholarOfAthreos copy() {
        return new ScholarOfAthreos(this);
    }
}

class ScholarOfAthreosEffect extends OneShotEffect {

    public ScholarOfAthreosEffect() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 1 life. You gain life equal to the life lost this way";
    }

    private ScholarOfAthreosEffect(final ScholarOfAthreosEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lifeLost = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if(opponent != null) {
                lifeLost += opponent.loseLife(1, game, source, false);
            }
        }
        game.getPlayer(source.getControllerId()).gainLife(lifeLost, game, source);
        return true;
    }

    @Override
    public ScholarOfAthreosEffect copy() {
        return new ScholarOfAthreosEffect(this);
    }

}
