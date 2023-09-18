
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class UnlivingPsychopath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power less than Unliving Psychopath's power");

    static {
        filter.add(new UnlivingPsychopathPowerLessThanSourcePredicate());
    }

    public UnlivingPsychopath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // {B}: Unliving Psychopath gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, -1, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.B)));

        // {B}, {tap}: Destroy target creature with power less than Unliving Psychopath's power.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private UnlivingPsychopath(final UnlivingPsychopath card) {
        super(card);
    }

    @Override
    public UnlivingPsychopath copy() {
        return new UnlivingPsychopath(this);
    }
}

class UnlivingPsychopathPowerLessThanSourcePredicate implements ObjectSourcePlayerPredicate<Permanent> {

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent sourcePermanent = input.getSource().getSourcePermanentOrLKI(game);
        return sourcePermanent != null && input.getObject().getPower().getValue() < sourcePermanent.getPower().getValue();
    }

    @Override
    public String toString() {
        return "power less than Unliving Psychopath's power";
    }
}
