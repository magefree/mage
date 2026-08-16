package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author muz
 */
public final class AvengersQuinjet extends CardImpl {

    private static final FilterCreatureCard filterHand = new FilterCreatureCard("a Hero creature card");
    private static final FilterCreatureCard filterGraveyard = new FilterCreatureCard("Hero creature card from your graveyard");

    static {
        filterHand.add(SubType.HERO.getPredicate());
        filterGraveyard.add(SubType.HERO.getPredicate());
    }

    public AvengersQuinjet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this Vehicle enters or attacks, choose one --
        // * You may put a Hero creature card from your hand onto the battlefield.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new PutCardFromHandOntoBattlefieldEffect(filterHand)
        );

        // * Return target Hero creature card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filterGraveyard));
        ability.addMode(mode);
        this.addAbility(ability);

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private AvengersQuinjet(final AvengersQuinjet card) {
        super(card);
    }

    @Override
    public AvengersQuinjet copy() {
        return new AvengersQuinjet(this);
    }
}
