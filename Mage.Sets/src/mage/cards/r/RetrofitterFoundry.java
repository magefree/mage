package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.RetrofitterFoundryToken;
import mage.game.permanent.token.ServoToken;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class RetrofitterFoundry extends CardImpl {

    private static final FilterControlledPermanent filter1 = new FilterControlledPermanent("a Servo");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("a Thopter");

    static {
        filter1.add(new SubtypePredicate(SubType.SERVO));
        filter2.add(new SubtypePredicate(SubType.THOPTER));
    }

    public RetrofitterFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {3}: Untap Retrofitter Foundry.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new UntapSourceEffect(),
                new GenericManaCost(3))
        );

        // {2}, {T}: Create a 1/1 colorless Servo artifact creature token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ServoToken()),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {1}, {T}, Sacrifice a Servo: Create a 1/1 colorless Thopter artifact creature token with flying.
        ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ThopterColorlessToken()),
                new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter1)));
        this.addAbility(ability);

        // {T}, Sacrifice a Thopter: Create a 4/4 colorless Construct artifact creature token.
        ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new RetrofitterFoundryToken()),
                new TapSourceCost()
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability);
    }

    public RetrofitterFoundry(final RetrofitterFoundry card) {
        super(card);
    }

    @Override
    public RetrofitterFoundry copy() {
        return new RetrofitterFoundry(this);
    }
}
