package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LumengridSentinel extends CardImpl {

    public LumengridSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an artifact enters the battlefield under your control, you may tap target permanent.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new TapTargetEffect(), StaticFilters.FILTER_PERMANENT_ARTIFACT_AN, true
        );
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private LumengridSentinel(final LumengridSentinel card) {
        super(card);
    }

    @Override
    public LumengridSentinel copy() {
        return new LumengridSentinel(this);
    }
}
