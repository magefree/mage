package mage.cards.h;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HulkingRaptor extends CardImpl {

    public HulkingRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // At the beginning of your precombat main phase, add {G}{G}.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new BasicManaEffect(Mana.GreenMana(2)), TargetController.YOU, false
        ));
    }

    private HulkingRaptor(final HulkingRaptor card) {
        super(card);
    }

    @Override
    public HulkingRaptor copy() {
        return new HulkingRaptor(this);
    }
}
