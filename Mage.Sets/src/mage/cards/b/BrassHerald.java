
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.game.Game;

/**
 *
 * @author LoneFox
 */
public final class BrassHerald extends CardImpl {

    public BrassHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Brass Herald enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // When Brass Herald enters the battlefield, reveal the top four cards of your library. Put all creature cards of the chosen type revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BrassHeraldEntersEffect()));

        // Creatures of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllOfChosenSubtypeEffect(1, 1, Duration.WhileOnBattlefield, false)));
    }

    private BrassHerald(final BrassHerald card) {
        super(card);
    }

    @Override
    public BrassHerald copy() {
        return new BrassHerald(this);
    }
}

class BrassHeraldEntersEffect extends OneShotEffect {

    public BrassHeraldEntersEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal the top four cards of your library. Put all creature cards of the chosen type revealed this way into your hand and the rest on the bottom of your library in any order";
    }

    private BrassHeraldEntersEffect(final BrassHeraldEntersEffect effect) {
        super(effect);
    }

    @Override
    public BrassHeraldEntersEffect copy() {
        return new BrassHeraldEntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterCard("creature cards of the chosen type");
        filter.add(ChosenSubtypePredicate.TRUE);
        return new RevealLibraryPutIntoHandEffect(4, filter, Zone.LIBRARY).apply(game, source);
    }
}
