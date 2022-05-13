package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GrixisSojourners extends CardImpl {

    public GrixisSojourners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When you cycle Grixis Sojourners or it dies, you may exile target card from a graveyard.
        Ability ability = new OrTriggeredAbility(Zone.ALL,
                new ExileTargetEffect(),
                true,
                "When you cycle {this} or it dies, ",
                new CycleTriggeredAbility(null, true),
                new DiesSourceTriggeredAbility(null, true)
        );
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // Cycling {2}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{B}")));
    }

    private GrixisSojourners(final GrixisSojourners card) {
        super(card);
    }

    @Override
    public GrixisSojourners copy() {
        return new GrixisSojourners(this);
    }
}
