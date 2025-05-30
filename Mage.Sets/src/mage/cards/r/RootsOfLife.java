package mage.cards.r;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ModeChoice;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author fubs
 */
public final class RootsOfLife extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("a land of the chosen type an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(RootsOfLifePredicate.instance);
    }

    public RootsOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // As Roots of Life enters the battlefield, choose Island or Swamp.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.ISLAND, ModeChoice.SWAMP)));

        // Whenever a land of the chosen type an opponent controls becomes tapped, you gain 1 life.
        this.addAbility(new BecomesTappedTriggeredAbility(new GainLifeEffect(1), false, filter));
    }

    private RootsOfLife(final RootsOfLife card) {
        super(card);
    }

    @Override
    public RootsOfLife copy() {
        return new RootsOfLife(this);
    }
}

enum RootsOfLifePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        if (ModeChoice.ISLAND.checkMode(game, input.getSource())) {
            return input.getObject().hasSubtype(SubType.ISLAND, game);
        } else if (ModeChoice.SWAMP.checkMode(game, input.getSource())) {
            return input.getObject().hasSubtype(SubType.SWAMP, game);
        } else {
            return false;
        }
    }
}
