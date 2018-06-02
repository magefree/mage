
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class SilentSentinel extends CardImpl {

    private static final FilterCard filter = new FilterCard("enchantment card from your graveyard");
    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public SilentSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Silent Sentinel attacks, you may return target enchantment card from your graveyard to the battlefield.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    public SilentSentinel(final SilentSentinel card) {
        super(card);
    }

    @Override
    public SilentSentinel copy() {
        return new SilentSentinel(this);
    }
}
