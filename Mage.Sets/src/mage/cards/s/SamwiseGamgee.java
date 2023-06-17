package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterHistoricCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SamwiseGamgee extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another nontoken creature");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.FOOD, "Foods");
    private static final FilterCard filter3 = new FilterHistoricCard("historic card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public SamwiseGamgee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another nontoken creature enters the battlefield under your control, create a Food token.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new CreateTokenEffect(new FoodToken()), filter));

        // Sacrifice three Foods: Return target historic card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToHandTargetEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(3, filter2))
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter3));
        this.addAbility(ability);
    }

    private SamwiseGamgee(final SamwiseGamgee card) {
        super(card);
    }

    @Override
    public SamwiseGamgee copy() {
        return new SamwiseGamgee(this);
    }
}
