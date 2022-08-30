package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class BattleflySwarm extends CardImpl {

    public BattleflySwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {B}: Battlefly Swarm gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
    }

    private BattleflySwarm(final BattleflySwarm card) {
        super(card);
    }

    @Override
    public BattleflySwarm copy() {
        return new BattleflySwarm(this);
    }
}
