package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThopterArchitect extends CardImpl {

    public ThopterArchitect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever an artifact enters the battlefield under your control, target creature gains flying until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new GainAbilityTargetEffect(FlyingAbility.getInstance()),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ThopterArchitect(final ThopterArchitect card) {
        super(card);
    }

    @Override
    public ThopterArchitect copy() {
        return new ThopterArchitect(this);
    }
}
