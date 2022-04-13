package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SocialClimber extends CardImpl {

    public SocialClimber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Alliance — Whenever another creature enters the battlefield under your control, you gain 1 life.
        this.addAbility(new AllianceAbility(new GainLifeEffect(1)));
    }

    private SocialClimber(final SocialClimber card) {
        super(card);
    }

    @Override
    public SocialClimber copy() {
        return new SocialClimber(this);
    }
}
