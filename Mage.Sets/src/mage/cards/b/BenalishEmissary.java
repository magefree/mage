package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class BenalishEmissary extends CardImpl {

    public BenalishEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Kicker {1}{G}
        this.addAbility(new KickerAbility("{1}{G}"));

        // When Benalish Emissary enters the battlefield, if it was kicked, destroy target land.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect()).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private BenalishEmissary(final BenalishEmissary card) {
        super(card);
    }

    @Override
    public BenalishEmissary copy() {
        return new BenalishEmissary(this);
    }
}
