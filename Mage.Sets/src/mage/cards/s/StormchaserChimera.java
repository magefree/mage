
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class StormchaserChimera extends CardImpl {

    public StormchaserChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{R}");
        this.subtype.add(SubType.CHIMERA);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {2}{U}{R}: Scry 1, then reveal the top card of your library. Stormchaser Chimera gets +X/+0 until end of turn, where X is that card's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1, false), new ManaCostsImpl("{2}{U}{R}"));
        ability.addEffect(new StormchaserChimeraEffect());
        this.addAbility(ability);
    }

    private StormchaserChimera(final StormchaserChimera card) {
        super(card);
    }

    @Override
    public StormchaserChimera copy() {
        return new StormchaserChimera(this);
    }
}

class StormchaserChimeraEffect extends OneShotEffect {

    public StormchaserChimeraEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then reveal the top card of your library. Stormchaser Chimera gets +X/+0 until end of turn, where X is that card's mana value";
    }

    public StormchaserChimeraEffect(final StormchaserChimeraEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield((source.getSourceId()));
        if (player == null || sourcePermanent == null) {
            return false;
        }
        if (player.getLibrary().hasCards()) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            player.revealCards(sourcePermanent.getName(), cards, game);

            if (card != null) {
                game.addEffect(new BoostSourceEffect(card.getManaValue(), 0, Duration.EndOfTurn), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public StormchaserChimeraEffect copy() {
        return new StormchaserChimeraEffect(this);
    }
}
