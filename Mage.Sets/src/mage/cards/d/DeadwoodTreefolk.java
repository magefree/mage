package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DeadwoodTreefolk extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("another target creature card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public DeadwoodTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Vanishing 3
        this.addAbility(new VanishingAbility(3));

        // When Deadwood Treefolk enters the battlefield or leaves the battlefield, return another target creature card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldOrLeavesSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        Target target = new TargetCardInYourGraveyard(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private DeadwoodTreefolk(final DeadwoodTreefolk card) {
        super(card);
    }

    @Override
    public DeadwoodTreefolk copy() {
        return new DeadwoodTreefolk(this);
    }
}
