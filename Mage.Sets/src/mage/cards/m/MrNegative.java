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
 *
 * @author Jmlundeen
 */
public final class MrNegative extends CardImpl {

    public MrNegative(UUID ownerId, CardSetInfo setInfo) {
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

        // When Mr. Negative enters, you may exchange your life total with target opponent. If you lose life this way, draw that many cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MrNegativeEffect(), true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private MrNegative(final MrNegative card) {
        super(card);
    }

    @Override
    public MrNegative copy() {
        return new MrNegative(this);
    }
}

class MrNegativeEffect extends OneShotEffect {

    public MrNegativeEffect() {
        super(Outcome.Neutral);
        staticText = "When {this} enters, you may exchange your life total with target opponent. If you lose life this way, draw that many cards.";
    }

    protected MrNegativeEffect(final MrNegativeEffect effect) {
        super(effect);
    }

    @Override
    public MrNegativeEffect copy() {
        return new MrNegativeEffect(this);
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
