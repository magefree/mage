package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class AerialCaravan extends CardImpl {

    public AerialCaravan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{U}{U}: Exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new SimpleActivatedAbility(new AerialCaravanExileEffect(), new ManaCostsImpl("{1}{U}{U}")));
    }

    private AerialCaravan(final AerialCaravan card) {
        super(card);
    }

    @Override
    public AerialCaravan copy() {
        return new AerialCaravan(this);
    }
}

class AerialCaravanExileEffect extends OneShotEffect {

    public AerialCaravanExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile the top card of your library. Until end of turn, you may play that card";
    }

    public AerialCaravanExileEffect(final AerialCaravanExileEffect effect) {
        super(effect);
    }

    @Override
    public AerialCaravanExileEffect copy() {
        return new AerialCaravanExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && controller != null && controller.getLibrary().hasCards()) {
            Library library = controller.getLibrary();
            Card card = library.getFromTop(game);
            if (card != null) {
                String exileName = sourcePermanent.getIdName() + " <this card may be played the turn it was exiled>";
                controller.moveCardsToExile(card, source, game, true, source.getSourceId(), exileName);
                ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
