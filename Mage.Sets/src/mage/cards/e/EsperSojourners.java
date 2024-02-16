package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class EsperSojourners extends CardImpl {

    public EsperSojourners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}{U}{B}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When you cycle Esper Sojourners or it dies, you may tap or untap target permanent.
        Ability ability = new OrTriggeredAbility(Zone.ALL,
                new MayTapOrUntapTargetEffect(),
                false,
                "When you cycle {this} or it dies, ",
                new CycleTriggeredAbility(null, false),
                new DiesSourceTriggeredAbility(null, false)
        );
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // Cycling {2}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{U}")));
    }

    private EsperSojourners(final EsperSojourners card) {
        super(card);
    }

    @Override
    public EsperSojourners copy() {
        return new EsperSojourners(this);
    }
}
