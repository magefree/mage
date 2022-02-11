
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VedalkenShackles extends CardImpl {

    private static final FilterCreaturePermanent controllableCreatures = new FilterCreaturePermanent("creature with power less than or equal to the number of Islands you control");
    static {
        controllableCreatures.add(new PowerIslandPredicate());
    }


    public VedalkenShackles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // You may choose not to untap Vedalken Shackles during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {2}, {tap}: Gain control of target creature with power less than or equal to the number of Islands you control for as long as Vedalken Shackles remains tapped.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.Custom), SourceTappedCondition.TAPPED,
                "Gain control of target creature with power less than or equal to the number of Islands you control for as long as {this} remains tapped");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(controllableCreatures));
        this.addAbility(ability);
    }

    private VedalkenShackles(final VedalkenShackles card) {
        super(card);
    }

    @Override
    public VedalkenShackles copy() {
        return new VedalkenShackles(this);
    }
}

class PowerIslandPredicate implements ObjectSourcePlayerPredicate<Permanent> {

    static final FilterLandPermanent filter = new FilterLandPermanent("Island");
    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = input.getObject();
        if (permanent != null) {
            int islands = game.getBattlefield().countAll(filter, input.getPlayerId(), game);
            if (permanent.getPower().getValue() <= islands) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "creature with power less than or equal to the number of Islands you control";
    }
}
