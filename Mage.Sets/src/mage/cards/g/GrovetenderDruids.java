
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.GrovetenderDruidsPlantToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class GrovetenderDruids extends CardImpl {

    public GrovetenderDruids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Rally</i>-Whenever Grovetender Druids or another Ally enters the battlefield under your control, you may pay {1}.
        // If you do, create a 1/1 green Plant creature token.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new GrovetenderDruidsEffect(), false));
    }

    public GrovetenderDruids(final GrovetenderDruids card) {
        super(card);
    }

    @Override
    public GrovetenderDruids copy() {
        return new GrovetenderDruids(this);
    }
}

class GrovetenderDruidsEffect extends OneShotEffect {

    GrovetenderDruidsEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {1}. If you do, create a 1/1 green Plant creature token";
    }

    GrovetenderDruidsEffect(final GrovetenderDruidsEffect effect) {
        super(effect);
    }

    @Override
    public GrovetenderDruidsEffect copy() {
        return new GrovetenderDruidsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.chooseUse(Outcome.BoostCreature, "Do you want to to pay {1}?", source, game)) {
                Cost cost = new ManaCostsImpl("{1}");
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    new CreateTokenEffect(new GrovetenderDruidsPlantToken()).apply(game, source);
                }
                return true;
            }
        }
        return false;
    }
}
