package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class JundSojourners extends CardImpl {

    public JundSojourners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{G}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When you cycle Jund Sojourners or it dies, you may have it deal 1 damage to any target.
        Ability ability = new OrTriggeredAbility(Zone.ALL,
                new DamageTargetEffect(1).setText("it deal 1 damage to any target"),
                true,
                "When you cycle {this} or it dies, ",
                new CycleTriggeredAbility(null, true),
                new DiesSourceTriggeredAbility(null, true)
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Cycling {2}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{R}")));
    }

    private JundSojourners(final JundSojourners card) {
        super(card);
    }

    @Override
    public JundSojourners copy() {
        return new JundSojourners(this);
    }
}
