package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SourceDealsNoncombatDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class ChandrasPyreling extends CardImpl {

    public ChandrasPyreling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a source you control deals noncombat damage to an opponent, Chandra's Pyreling gets +1/+0 and gains double strike until end of turn.
        Ability ability = new SourceDealsNoncombatDamageToOpponentTriggeredAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn).setText("this creature gets +1/+0"));
        ability.addEffect(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn).setText("and gains double strike until end of turn"));
        this.addAbility(ability);
    }

    private ChandrasPyreling(final ChandrasPyreling card) {
        super(card);
    }

    @Override
    public ChandrasPyreling copy() {
        return new ChandrasPyreling(this);
    }
}
