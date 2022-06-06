
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GempalmStrider extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Elf creatures");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public GempalmStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Cycling {2}{G}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{G}{G}")));
        // When you cycle Gempalm Strider, Elf creatures get +2/+2 until end of turn.
        this.addAbility(new CycleTriggeredAbility(new BoostAllEffect(2,2,Duration.EndOfTurn, filter, false)));
    }

    private GempalmStrider(final GempalmStrider card) {
        super(card);
    }

    @Override
    public GempalmStrider copy() {
        return new GempalmStrider(this);
    }
}
