
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.MorphAbility;
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
 * @author fireshoes
 */
public final class WallOfDeceit extends CardImpl {

    public WallOfDeceit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {3}: Turn Wall of Deceit face down.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WallOfDeceitEffect(), new ManaCostsImpl<>("{3}")));

        // Morph {U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{U}")));
    }

    private WallOfDeceit(final WallOfDeceit card) {
        super(card);
    }

    @Override
    public WallOfDeceit copy() {
        return new WallOfDeceit(this);
    }
}

class WallOfDeceitEffect extends OneShotEffect {

    public WallOfDeceitEffect() {
        super(Outcome.Detriment);
        this.staticText = "Turn {this} face down";
    }

    public WallOfDeceitEffect(final WallOfDeceitEffect effect) {
        super(effect);
    }

    @Override
    public WallOfDeceitEffect copy() {
        return new WallOfDeceitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null
                && source.getSourceObjectZoneChangeCounter() == sourcePermanent.getZoneChangeCounter(game) // in case source was blinked after ability was set to stack
                && !sourcePermanent.isFaceDown(game)) {
            sourcePermanent.setFaceDown(true, game);
        }
        return true;
    }
}
