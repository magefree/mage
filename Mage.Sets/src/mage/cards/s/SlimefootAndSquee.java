package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlimefootAndSquee extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.SAPROLING, "a Saproling");
    private static final FilterCard filter2 = new FilterCreatureCard("another creature card from your graveyard");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public SlimefootAndSquee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Slimefoot and Squee enters the battlefield or attacks, create a 1/1 green Saproling creature token.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CreateTokenEffect(new SaprolingToken())));

        // {1}{B}{R}{G}, Sacrifice a Saproling: Return Slimefoot and Squee and up to one other target creature card from your graveyard to the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect()
                        .setText("return {this}"),
                new ManaCostsImpl<>("{1}{B}{R}{G}")
        );
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("and up to one other target creature card from your graveyard to the battlefield"));
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private SlimefootAndSquee(final SlimefootAndSquee card) {
        super(card);
    }

    @Override
    public SlimefootAndSquee copy() {
        return new SlimefootAndSquee(this);
    }
}
