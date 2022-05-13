
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DeathmistRaptor extends CardImpl {

    public DeathmistRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a permanent you control is turned face up, you may return Deathmist Raptor from your graveyard to the battlefield face up or face down.
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(Zone.GRAVEYARD, new DeathmistRaptorEffect(), new FilterControlledPermanent("a permanent you control"), false, true));

        // Megamorph {4}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{4}{G}"), true));
    }

    private DeathmistRaptor(final DeathmistRaptor card) {
        super(card);
    }

    @Override
    public DeathmistRaptor copy() {
        return new DeathmistRaptor(this);
    }
}

class DeathmistRaptorEffect extends OneShotEffect {

    public DeathmistRaptorEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may return {this} from your graveyard to the battlefield face up or face down";
    }

    public DeathmistRaptorEffect(final DeathmistRaptorEffect effect) {
        super(effect);
    }

    @Override
    public DeathmistRaptorEffect copy() {
        return new DeathmistRaptorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (controller != null && (sourceObject instanceof Card)) {
            return controller.moveCards((Card) sourceObject, Zone.BATTLEFIELD, source, game, false,
                    controller.chooseUse(Outcome.Detriment, "Return " + sourceObject.getLogName() + " face down to battlefield (otherwise face up)?", source, game),
                    false, null);
        }
        return false;
    }
}
