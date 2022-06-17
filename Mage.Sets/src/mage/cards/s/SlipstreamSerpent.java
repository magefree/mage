
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class SlipstreamSerpent extends CardImpl {

    public SlipstreamSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Slipstream Serpent can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.ISLAND, "an Island"))));

        // When you control no Islands, sacrifice Slipstream Serpent.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                new FilterLandPermanent(SubType.ISLAND, "no Islands"), ComparisonType.EQUAL_TO, 0,
                new SacrificeSourceEffect()));

        // Morph {5}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{5}{U}")));
    }

    private SlipstreamSerpent(final SlipstreamSerpent card) {
        super(card);
    }

    @Override
    public SlipstreamSerpent copy() {
        return new SlipstreamSerpent(this);
    }
}
