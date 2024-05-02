package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhiredMirrorOfTheWilds extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("token you control that entered the battlefield this turn");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public GhiredMirrorOfTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Nontoken creatures you control have "{T}: Create a token that's a copy of target token you control that entered the battlefield this turn."
        Ability ability = new SimpleActivatedAbility(new CreateTokenCopyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_NON_TOKEN
        )));
    }

    private GhiredMirrorOfTheWilds(final GhiredMirrorOfTheWilds card) {
        super(card);
    }

    @Override
    public GhiredMirrorOfTheWilds copy() {
        return new GhiredMirrorOfTheWilds(this);
    }
}
