package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.Objects;
import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class Mindshrieker extends CardImpl {

    public Mindshrieker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}: Target player puts the top card of their library into their graveyard. Mindshrieker gets +X/+X until end of turn, where X is that card's converted mana cost.
        Ability ability = new SimpleActivatedAbility(new MindshriekerEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private Mindshrieker(final Mindshrieker card) {
        super(card);
    }

    @Override
    public Mindshrieker copy() {
        return new Mindshrieker(this);
    }
}

class MindshriekerEffect extends OneShotEffect {

    MindshriekerEffect() {
        super(Outcome.Detriment);
        staticText = "Target player mills a card. {this} gets +X/+X until end of turn, " +
                "where X is the milled card's mana value";
    }

    private MindshriekerEffect(final MindshriekerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }
        int totalCMC = targetPlayer
                .millCards(1, source, game)
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        if (totalCMC > 0) {
            game.addEffect(new BoostSourceEffect(totalCMC, totalCMC, Duration.EndOfTurn), source);
        }
        return false;
    }

    @Override
    public MindshriekerEffect copy() {
        return new MindshriekerEffect(this);
    }

}
