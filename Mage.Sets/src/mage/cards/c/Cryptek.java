package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Cryptek extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target artifact creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public Cryptek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.NECRON, SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{B}, {T}: Choose another target artifact creature you control. When that creature dies this turn, return it to the battlefield tapped under your control.
        Ability ability = new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new WhenTargetDiesDelayedTriggeredAbility(
                                new ReturnFromGraveyardToBattlefieldTargetEffect(true)
                                        .setText("return it to the battlefield tapped under your control"),
                                SetTargetPointer.CARD
                        ),
                        true, "Choose another target artifact creature you control. "
                ),
                new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private Cryptek(final Cryptek card) {
        super(card);
    }

    @Override
    public Cryptek copy() {
        return new Cryptek(this);
    }
}
