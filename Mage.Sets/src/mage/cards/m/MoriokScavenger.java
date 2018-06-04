
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LoneFox
 */
public final class MoriokScavenger extends CardImpl {

    final static FilterCreatureCard filter = new FilterCreatureCard("artifact creature card from your graveyard");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public MoriokScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Moriok Scavenger enters the battlefield, you may return target artifact creature card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public MoriokScavenger(final MoriokScavenger card) {
        super(card);
    }

    @Override
    public MoriokScavenger copy() {
        return new MoriokScavenger(this);
    }
}
