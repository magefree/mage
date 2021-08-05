package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RenegadeReaper extends CardImpl {

    public RenegadeReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Renegade Reaper enters the battlefield, mill four cards. If at least one Angel card is milled this way, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RenegadeReaperEffect()));
    }

    private RenegadeReaper(final RenegadeReaper card) {
        super(card);
    }

    @Override
    public RenegadeReaper copy() {
        return new RenegadeReaper(this);
    }
}

class RenegadeReaperEffect extends OneShotEffect {

    RenegadeReaperEffect() {
        super(Outcome.Benefit);
        staticText = "mill four cards. If at least one Angel card is milled this way, you gain 4 life";
    }

    private RenegadeReaperEffect(final RenegadeReaperEffect effect) {
        super(effect);
    }

    @Override
    public RenegadeReaperEffect copy() {
        return new RenegadeReaperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.millCards(4, source, game)
                .getCards(game)
                .stream()
                .anyMatch(card -> card.hasSubtype(SubType.ANGEL, game))) {
            player.gainLife(4, game, source);
        }
        return true;
    }
}
