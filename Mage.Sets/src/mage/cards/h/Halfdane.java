
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author L_J
 */
public final class Halfdane extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature other than Halfdane");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public Halfdane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, change Halfdane's base power and toughness to the power and toughness of target creature other than Halfdane until the end of your next upkeep.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new HalfdaneUpkeepEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private Halfdane(final Halfdane card) {
        super(card);
    }

    @Override
    public Halfdane copy() {
        return new Halfdane(this);
    }
}

class HalfdaneUpkeepEffect extends OneShotEffect {

    HalfdaneUpkeepEffect() {
        super(Outcome.Detriment);
        this.staticText = "change {this}'s base power and toughness to the power and toughness of target creature other than Halfdane until the end of your next upkeep";
    }

    private HalfdaneUpkeepEffect(final HalfdaneUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public HalfdaneUpkeepEffect copy() {
        return new HalfdaneUpkeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (controller == null || permanent == null) {
            return false;
        }

        ContinuousEffect effect = new SetBasePowerToughnessSourceEffect(
            permanent.getPower().getValue(),
            permanent.getToughness().getValue(),
            Duration.UntilYourNextUpkeepStep
        );
        game.addEffect(effect, source);
        return true;
    }
}
