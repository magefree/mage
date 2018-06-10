
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
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

/**
 *
 * @author LevelX2
 */
public final class FlickeringSpirit extends CardImpl {

    public FlickeringSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{W}: Exile Flickering Spirit, then return it to the battlefield under its owner's control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new FlickeringSpiritEffect(), new ManaCostsImpl("{3}{W}")));

    }

    public FlickeringSpirit(final FlickeringSpirit card) {
        super(card);
    }

    @Override
    public FlickeringSpirit copy() {
        return new FlickeringSpirit(this);
    }
}

class FlickeringSpiritEffect extends OneShotEffect {

    public FlickeringSpiritEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile {this}, then return it to the battlefield under its owner's control";
    }

    public FlickeringSpiritEffect(final FlickeringSpiritEffect effect) {
        super(effect);
    }

    @Override
    public FlickeringSpiritEffect copy() {
        return new FlickeringSpiritEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if (permanent.moveToExile(source.getSourceId(), "Flickering Spirit", source.getSourceId(), game)) {
                Card card = game.getExile().getCard(source.getSourceId(), game);
                if (card != null) {
                    return card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
                }
            }
        }
        return false;
    }
}
