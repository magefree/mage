package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PolukranosReborn extends CardImpl {

    public PolukranosReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.p.PolukranosEngineOfRuin.class;

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {6}{W/P}: Transform Polukranos Reborn. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{6}{W/P}")));
    }

    private PolukranosReborn(final PolukranosReborn card) {
        super(card);
    }

    @Override
    public PolukranosReborn copy() {
        return new PolukranosReborn(this);
    }
}
