package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DimensionalInfiltrator extends CardImpl {

    public DimensionalInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{C}: Exile the top card of target opponent's library. If it's a land card, you may return Dimensional Infiltrator to its owner's hand.
        Ability ability = new SimpleActivatedAbility(new DimensionalInfiltratorEffect(), new ManaCostsImpl<>("{1}{C}"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DimensionalInfiltrator(final DimensionalInfiltrator card) {
        super(card);
    }

    @Override
    public DimensionalInfiltrator copy() {
        return new DimensionalInfiltrator(this);
    }
}

class DimensionalInfiltratorEffect extends OneShotEffect {

    DimensionalInfiltratorEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "exile the top card of target opponent's library. " +
                "If it's a land card, you may return {this} to its owner's hand";
    }

    private DimensionalInfiltratorEffect(final DimensionalInfiltratorEffect effect) {
        super(effect);
    }

    @Override
    public DimensionalInfiltratorEffect copy() {
        return new DimensionalInfiltratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (player == null || opponent == null) {
            return false;
        }
        Card card = opponent.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (!card.isLand(game)) {
            return true;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null
                && player.chooseUse(outcome, "Return " + permanent.getName() + " to its owner's hand?", source, game)
                && player.moveCards(permanent, Zone.HAND, source, game);
    }
}
