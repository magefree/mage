package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.FilterPermanentThisOrAnother;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RatToken;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RatKingPalePiper extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanentThisOrAnother(
        StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, false
    );

    public RatKingPalePiper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Rat King or another nontoken creature you control leaves the battlefield, create a 1/1 black Rat creature token.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(new CreateTokenEffect(new RatToken()), filter));

        // {2}, Sacrifice a token: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_TOKEN));
        this.addAbility(ability);
    }

    private RatKingPalePiper(final RatKingPalePiper card) {
        super(card);
    }

    @Override
    public RatKingPalePiper copy() {
        return new RatKingPalePiper(this);
    }
}
