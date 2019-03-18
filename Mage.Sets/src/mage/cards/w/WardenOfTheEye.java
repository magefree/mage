
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class WardenOfTheEye extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature, nonland card from your graveyard");

    static {
        filter.add(new OwnerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public WardenOfTheEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{R}{W}");
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Warden of the Eye enters the battlefield, return target noncreature, nonland card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCard(Zone.GRAVEYARD, filter));
        this.addAbility(ability);
    }

    public WardenOfTheEye(final WardenOfTheEye card) {
        super(card);
    }

    @Override
    public WardenOfTheEye copy() {
        return new WardenOfTheEye(this);
    }
}
