package mage.cards.s;

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
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK;

/**
 * @author LoneFox
 */
public final class ShivanEmissary extends CardImpl {

    public ShivanEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {1}{B}
        this.addAbility(new KickerAbility("{1}{B}"));

        // When Shivan Emissary enters the battlefield, if it was kicked, destroy target nonblack creature. It can't be regenerated.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(true)).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetPermanent(FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.addAbility(ability);
    }

    private ShivanEmissary(final ShivanEmissary card) {
        super(card);
    }

    @Override
    public ShivanEmissary copy() {
        return new ShivanEmissary(this);
    }
}
