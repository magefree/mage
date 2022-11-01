package mage.cards.r;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReconstructedThopter extends CardImpl {

    public ReconstructedThopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Unearth {2}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{2}")));
    }

    private ReconstructedThopter(final ReconstructedThopter card) {
        super(card);
    }

    @Override
    public ReconstructedThopter copy() {
        return new ReconstructedThopter(this);
    }
}
