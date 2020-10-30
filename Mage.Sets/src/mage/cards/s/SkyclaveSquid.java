package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyclaveSquid extends CardImpl {

    public SkyclaveSquid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SQUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Landfall — Whenever a land enters the battlefield under your control, Skyclave Squid can attack this turn as though it didn't have defender.
        this.addAbility(new LandfallAbility(new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn)));
    }

    private SkyclaveSquid(final SkyclaveSquid card) {
        super(card);
    }

    @Override
    public SkyclaveSquid copy() {
        return new SkyclaveSquid(this);
    }
}
