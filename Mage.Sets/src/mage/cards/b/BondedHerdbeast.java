package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BondedHerdbeast extends CardImpl {

    public BondedHerdbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.p.PlatedKilnbeast.class;

        // {4}{R/P}: Transform Bonded Herdbeast. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{R/P}")));
    }

    private BondedHerdbeast(final BondedHerdbeast card) {
        super(card);
    }

    @Override
    public BondedHerdbeast copy() {
        return new BondedHerdbeast(this);
    }
}
