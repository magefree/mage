package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CunningGeysermage extends CardImpl {

    public CunningGeysermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Kicker {2}{U}
        this.addAbility(new KickerAbility("{2}{U}"));

        // When Cunning Geysermage enters the battlefield, if it was kicked, return up to one other target creature to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()
                .setText("return up to one other target creature to its owner's hand")).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private CunningGeysermage(final CunningGeysermage card) {
        super(card);
    }

    @Override
    public CunningGeysermage copy() {
        return new CunningGeysermage(this);
    }
}
