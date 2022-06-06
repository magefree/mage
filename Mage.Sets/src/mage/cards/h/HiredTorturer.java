

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */


public final class HiredTorturer extends CardImpl {

    public HiredTorturer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {3}{B}, {T}: Target opponent loses 2 life and reveals a card at random from their hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2),new ManaCostsImpl<>("{3}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new HiredTorturerEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    public HiredTorturer (final HiredTorturer card) {
        super(card);
    }

    @Override
    public HiredTorturer copy() {
        return new HiredTorturer(this);
    }

}

class HiredTorturerEffect extends OneShotEffect {

    public HiredTorturerEffect() {
        super(Outcome.Detriment);
        staticText = "and reveals a card at random from their hand";
    }

    public HiredTorturerEffect(final HiredTorturerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && !player.getHand().isEmpty()) {
            Cards revealed = new CardsImpl();
            revealed.add(player.getHand().getRandom(game));
            player.revealCards("Hired Torturer", revealed, game);
            return true;
        }
        return false;
    }

    @Override
    public HiredTorturerEffect copy() {
        return new HiredTorturerEffect(this);
    }

}
