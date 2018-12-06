package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ControlsPermanentsComparedToOpponentsCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerSourceEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class MonstrousHound extends CardImpl {

    public MonstrousHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HOUND);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Monstrous Hound can't attack unless you control more lands than defending player.
        Effect effect = new ConditionalRestrictionEffect(
                new CantAttackAnyPlayerSourceEffect(Duration.WhileOnBattlefield),
                new ControlsPermanentsComparedToOpponentsCondition(
                        ComparisonType.FEWER_THAN, 
                        new FilterLandPermanent()));
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, 
                effect.setText("{this} can't attack unless you control more lands than defending player")));

        // Monstrous Hound can't block unless you control more lands than attacking player.
        Effect effect2 = new ConditionalRestrictionEffect(
                new CantBlockSourceEffect(Duration.WhileOnBattlefield),
                new ControlsPermanentsComparedToOpponentsCondition(
                        ComparisonType.FEWER_THAN, 
                        new FilterLandPermanent()));
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, 
                effect2.setText("{this} can't block unless you control more lands than attacking player")));

    }

    public MonstrousHound(final MonstrousHound card) {
        super(card);
    }

    @Override
    public MonstrousHound copy() {
        return new MonstrousHound(this);
    }
}
