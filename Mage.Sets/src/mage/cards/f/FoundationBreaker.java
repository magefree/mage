package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EvokeAbility;
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
public final class FoundationBreaker extends CardImpl {

    public FoundationBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Foundation Breaker enters the battlefield, you may destroy target artifact or enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);

        // Evoke {1}{G}
        this.addAbility(new EvokeAbility("{1}{G}"));
    }

    private FoundationBreaker(final FoundationBreaker card) {
        super(card);
    }

    @Override
    public FoundationBreaker copy() {
        return new FoundationBreaker(this);
    }
}
