package mage.cards.k;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KitchenImp extends CardImpl {

    public KitchenImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Madness {B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{B}")));
    }

    private KitchenImp(final KitchenImp card) {
        super(card);
    }

    @Override
    public KitchenImp copy() {
        return new KitchenImp(this);
    }
}
