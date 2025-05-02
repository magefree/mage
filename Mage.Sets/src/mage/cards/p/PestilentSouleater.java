package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class PestilentSouleater extends CardImpl {

    public PestilentSouleater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {B/P}: Pestilent Souleater gains infect until end of turn. ({B/P} can be paid with either {B} or 2 life. A creature with infect deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                InfectAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{B/P}")));
    }

    private PestilentSouleater(final PestilentSouleater card) {
        super(card);
    }

    @Override
    public PestilentSouleater copy() {
        return new PestilentSouleater(this);
    }
}
