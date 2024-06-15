package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CastleEmbereth extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MOUNTAIN);
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public CastleEmbereth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Castle Embereth enters the battlefield tapped unless you control a Mountain.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {1}{R}{R}, {T}: Creatures you control get +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}{R}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CastleEmbereth(final CastleEmbereth card) {
        super(card);
    }

    @Override
    public CastleEmbereth copy() {
        return new CastleEmbereth(this);
    }
}
