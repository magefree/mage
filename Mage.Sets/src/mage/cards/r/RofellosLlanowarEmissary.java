package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Backfir3
 */
public final class RofellosLlanowarEmissary extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forest you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public RofellosLlanowarEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
		
        // {tap}: Add {G} for each Forest you control.
		this.addAbility(new DynamicManaAbility(Mana.GreenMana(1), new PermanentsOnBattlefieldCount(filter))
                .addHint(new ValueHint("Forests you control", new PermanentsOnBattlefieldCount(filter)))
        );
    }

    private RofellosLlanowarEmissary(final RofellosLlanowarEmissary card) {
        super(card);
    }

    @Override
    public RofellosLlanowarEmissary copy() {
        return new RofellosLlanowarEmissary(this);
    }

}
