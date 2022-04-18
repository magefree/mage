package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UntapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaloFountain extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("tapped creature you control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public HaloFountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // {W}, {T}, Untap an tapped creature you control: Create a 1/1 green and white Citizen creature token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new CitizenGreenWhiteToken()), new ManaCostsImpl<>("{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new UntapTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

        // {W}{W}, {T}, Untap two tapped creatures you control: Draw a card.
        ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{W}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new UntapTargetCost(new TargetControlledPermanent(2, filter)));
        this.addAbility(ability);

        // {W}{W}{W}{W}{W}, {T}, Untap fifteen tapped creatures you control: You win the game.
        ability = new SimpleActivatedAbility(
                new WinGameSourceControllerEffect(), new ManaCostsImpl<>("{W}{W}{W}{W}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new UntapTargetCost(new TargetControlledPermanent(15, filter)));
        this.addAbility(ability);
    }

    private HaloFountain(final HaloFountain card) {
        super(card);
    }

    @Override
    public HaloFountain copy() {
        return new HaloFountain(this);
    }
}
