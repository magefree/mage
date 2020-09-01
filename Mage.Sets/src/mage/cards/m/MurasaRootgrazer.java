package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class MurasaRootgrazer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("basic land you control");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    public MurasaRootgrazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: You may put a basic land card from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_BASIC_LAND_A), new TapSourceCost()
        ));

        // {T}: Return target basic land you control to its owner's hand.
        Ability ability = new SimpleActivatedAbility(new ReturnToHandTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MurasaRootgrazer(final MurasaRootgrazer card) {
        super(card);
    }

    @Override
    public MurasaRootgrazer copy() {
        return new MurasaRootgrazer(this);
    }
}
