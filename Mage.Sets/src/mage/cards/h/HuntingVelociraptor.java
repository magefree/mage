package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.constants.SubType;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;

/**
 *
 * @author jimga150
 */
public final class HuntingVelociraptor extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("Dinosaur spells you cast");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
    }

    public HuntingVelociraptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Dinosaur spells you cast have prowl {2}{R}.
        // Based on Chief Engineer
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityControlledSpellsEffect(new ProwlAbility("{2}{R}"), filter)
        ));
    }

    private HuntingVelociraptor(final HuntingVelociraptor card) {
        super(card);
    }

    @Override
    public HuntingVelociraptor copy() {
        return new HuntingVelociraptor(this);
    }
}
