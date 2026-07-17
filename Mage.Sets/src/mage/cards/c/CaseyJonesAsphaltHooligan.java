package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class CaseyJonesAsphaltHooligan extends CardImpl {

    public CaseyJonesAsphaltHooligan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // {4}: Double Casey Jones's power until end of turn. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
            new BoostSourceEffect(SourcePermanentPowerValue.ALLOW_NEGATIVE, StaticValue.get(0), Duration.EndOfTurn)
                .setText("double {this}'s power until end of turn"),
            new ManaCostsImpl<>("{4}")
        );
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private CaseyJonesAsphaltHooligan(final CaseyJonesAsphaltHooligan card) {
        super(card);
    }

    @Override
    public CaseyJonesAsphaltHooligan copy() {
        return new CaseyJonesAsphaltHooligan(this);
    }
}
