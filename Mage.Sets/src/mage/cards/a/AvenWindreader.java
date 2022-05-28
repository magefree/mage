package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvenWindreader extends CardImpl {

    public AvenWindreader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{U}: Target player reveals the top card of their library.
        Ability ability = new SimpleActivatedAbility(new AvenWindreaderEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private AvenWindreader(final AvenWindreader card) {
        super(card);
    }

    @Override
    public AvenWindreader copy() {
        return new AvenWindreader(this);
    }
}

class AvenWindreaderEffect extends OneShotEffect {

    AvenWindreaderEffect() {
        super(Outcome.Benefit);
        staticText = "target player reveals the top card of their library";
    }

    private AvenWindreaderEffect(final AvenWindreaderEffect effect) {
        super(effect);
    }

    @Override
    public AvenWindreaderEffect copy() {
        return new AvenWindreaderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        player.revealCards(source, new CardsImpl(player.getLibrary().getFromTop(game)), game);
        return true;
    }
}
