package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RomanaII extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("token that entered the battlefield this turn");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public RomanaII(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {1}, {T}: Create a tapped token that's a copy of target token that entered the battlefield this turn.
        Ability ability = new SimpleActivatedAbility(new CreateTokenCopyTargetEffect(
                null, null, false, 1, true, false
        ), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private RomanaII(final RomanaII card) {
        super(card);
    }

    @Override
    public RomanaII copy() {
        return new RomanaII(this);
    }
}
