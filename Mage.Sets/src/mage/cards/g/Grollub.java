package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public final class Grollub extends CardImpl {

    public Grollub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Grollub is dealt damage, each opponent gains that much life.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new EachOpponentGainsLifeEffect(), false, false));

    }

    private Grollub(final Grollub card) {
        super(card);
    }

    @Override
    public Grollub copy() {
        return new Grollub(this);
    }
}

class EachOpponentGainsLifeEffect extends OneShotEffect {

    public EachOpponentGainsLifeEffect() {
        super(Outcome.Neutral);
        this.staticText = "each opponent gains that much life";
    }

    public EachOpponentGainsLifeEffect(final EachOpponentGainsLifeEffect effect) {
        super(effect);
    }

    @Override
    public EachOpponentGainsLifeEffect copy() {
        return new EachOpponentGainsLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getOpponents(source.getControllerId()).stream().map((opponentId) -> game.getPlayer(opponentId)).filter((opponent) -> (opponent != null)).forEachOrdered((opponent) -> {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                opponent.gainLife(amount, game, source);
            }
        });
        return true;
    }
}
