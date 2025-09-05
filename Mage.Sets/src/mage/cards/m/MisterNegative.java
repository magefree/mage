package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class MisterNegative extends CardImpl {

    public MisterNegative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Darkforce Inversion — When Mister Negative enters, you may exchange your life total with target opponent. If you lose life this way, draw that many cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MisterNegativeEffect(), true);
        ability.addTarget(new TargetOpponent());
        ability.withFlavorWord("Darkforce Inversion");
        this.addAbility(ability);
    }

    private MisterNegative(final MisterNegative card) {
        super(card);
    }

    @Override
    public MisterNegative copy() {
        return new MisterNegative(this);
    }
}

class MisterNegativeEffect extends OneShotEffect {

    MisterNegativeEffect() {
        super(Outcome.Neutral);
        staticText = "When {this} enters, you may exchange your life total with target opponent. If you lose life this way, draw that many cards.";
    }

    protected MisterNegativeEffect(final MisterNegativeEffect effect) {
        super(effect);
    }

    @Override
    public MisterNegativeEffect copy() {
        return new MisterNegativeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        int startingLife = controller.getLife();
        controller.exchangeLife(player, source, game);
        int lifeChange = startingLife - controller.getLife();
        if (lifeChange > 0) {
            controller.drawCards(lifeChange, source, game);
        }
        return true;
    }
}
