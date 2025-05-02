package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MigratingKetradon extends CardImpl {

    public MigratingKetradon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this creature enters, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(4)));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private MigratingKetradon(final MigratingKetradon card) {
        super(card);
    }

    @Override
    public MigratingKetradon copy() {
        return new MigratingKetradon(this);
    }
}
