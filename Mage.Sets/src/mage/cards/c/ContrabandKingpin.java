
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ContrabandKingpin extends CardImpl {

    public ContrabandKingpin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever an artifact enters the battlefield under your control, scry 1.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new ScryEffect(1, false),
                StaticFilters.FILTER_PERMANENT_ARTIFACT_AN
        ));
    }

    private ContrabandKingpin(final ContrabandKingpin card) {
        super(card);
    }

    @Override
    public ContrabandKingpin copy() {
        return new ContrabandKingpin(this);
    }
}
