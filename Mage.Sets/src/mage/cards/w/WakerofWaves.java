
package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;

import java.util.UUID;

/**
 *
 * @author mikalinn777
 */
public final class WakerofWaves extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public WakerofWaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.WHALE);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Creatures your opponents control get -1/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(-1, -0, Duration.WhileOnBattlefield, filter, false)));

        // {1}{U}, Discard Waker of Waves: Look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.HAND, new LookLibraryAndPickControllerEffect(StaticValue.get(2), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, false), new ManaCostsImpl("{1}{U}"));
        ability.addCost(new DiscardSourceCost());
        this.addAbility(ability);
    }

    public WakerofWaves(final WakerofWaves card) {
        super(card);
    }

    @Override
    public WakerofWaves copy() {
        return new WakerofWaves(this);
    }
}

