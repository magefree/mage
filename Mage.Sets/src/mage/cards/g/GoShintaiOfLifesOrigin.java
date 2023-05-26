package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ShrineToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoShintaiOfLifesOrigin extends CardImpl {

    private static final FilterCard filter
            = new FilterEnchantmentCard("enchantment card from your graveyard");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.SHRINE, "nontoken Shrine");

    static {
        filter2.add(TokenPredicate.FALSE);
    }

    public GoShintaiOfLifesOrigin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {W}{U}{B}{R}{G}, {T}: Return target enchantment card from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Whenever Go-Shintai of Life's Origin or another nontoken Shrine enters the battlefield under your control, create a 1/1 colorless Shrine enchantment creature token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new ShrineToken()),
                filter2, false, true
        ));
    }

    private GoShintaiOfLifesOrigin(final GoShintaiOfLifesOrigin card) {
        super(card);
    }

    @Override
    public GoShintaiOfLifesOrigin copy() {
        return new GoShintaiOfLifesOrigin(this);
    }
}
