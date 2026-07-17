package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RimekinRecluse extends CardImpl {

    public RimekinRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, return up to one other target creature to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private RimekinRecluse(final RimekinRecluse card) {
        super(card);
    }

    @Override
    public RimekinRecluse copy() {
        return new RimekinRecluse(this);
    }
}
