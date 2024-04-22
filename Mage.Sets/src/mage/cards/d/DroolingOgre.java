package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.TargetPlayerGainControlSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class DroolingOgre extends CardImpl {

    public DroolingOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a player casts an artifact spell, that player gains control of Drooling Ogre.
        this.addAbility(new SpellCastAllTriggeredAbility(new TargetPlayerGainControlSourceEffect(),
                StaticFilters.FILTER_SPELL_AN_ARTIFACT, false, SetTargetPointer.PLAYER));
    }

    private DroolingOgre(final DroolingOgre card) {
        super(card);
    }

    @Override
    public DroolingOgre copy() {
        return new DroolingOgre(this);
    }

}
