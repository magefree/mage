package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TalonGatesOfMadara extends CardImpl {

    public TalonGatesOfMadara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.GATE);

        // When Talon Gates of Madara enters the battlefield, up to one target creature phases out.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PhaseOutTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add one mana of any color.
        ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {4}: Put Talon Gates of Madara from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.HAND, new TalonGatesOfMadaraEffect(), new GenericManaCost(4)));
    }

    private TalonGatesOfMadara(final TalonGatesOfMadara card) {
        super(card);
    }

    @Override
    public TalonGatesOfMadara copy() {
        return new TalonGatesOfMadara(this);
    }
}

class TalonGatesOfMadaraEffect extends OneShotEffect {

    TalonGatesOfMadaraEffect() {
        super(Outcome.Benefit);
        staticText = "Put {this} from your hand onto the battlefield.";
    }

    private TalonGatesOfMadaraEffect(final TalonGatesOfMadaraEffect effect) {
        super(effect);
    }

    @Override
    public TalonGatesOfMadaraEffect copy() {
        return new TalonGatesOfMadaraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getHand().get(source.getSourceId(), game);
        if (card == null) {
            return false;
        }
        return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}