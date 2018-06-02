
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class DimensionalInfiltrator extends CardImpl {

    public DimensionalInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
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
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DimensionalInfiltratorEffect(), new ManaCostsImpl("{1}{C}"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public DimensionalInfiltrator(final DimensionalInfiltrator card) {
        super(card);
    }

    @Override
    public DimensionalInfiltrator copy() {
        return new DimensionalInfiltrator(this);
    }
}

class DimensionalInfiltratorEffect extends OneShotEffect {

    public DimensionalInfiltratorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile the top card of target opponent's library. If it's a land card, you may return Dimensional Infiltrator to its owner's hand";
    }

    public DimensionalInfiltratorEffect(final DimensionalInfiltratorEffect effect) {
        super(effect);
    }

    @Override
    public DimensionalInfiltratorEffect copy() {
        return new DimensionalInfiltratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (opponent == null || controller == null || sourceObject == null) {
            return false;
        }

        if (opponent.getLibrary().hasCards()) {
            Card card = opponent.getLibrary().getFromTop(game);
            if (card != null) {
                card.moveToExile(null, "Dimensional Infiltrator", source.getSourceId(), game);
                if (card.isLand()) {
                    if (controller.chooseUse(Outcome.Neutral, "Return " + sourceObject.getIdName() + " to its owner's hand?", source, game)) {
                        new ReturnToHandSourceEffect(true).apply(game, source);
                    }
                }
            }
        }
        return true;
    }
}
