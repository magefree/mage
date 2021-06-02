package mage.cards.b;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ReinforceAbility;
import mage.abilities.keyword.ScavengeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BannerhideKrushok extends CardImpl {

    public BannerhideKrushok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Reinforce 2—{1}{G}
        this.addAbility(new ReinforceAbility(2, new ManaCostsImpl<>("{1}{G}")));

        // Scavenge {5}{G}{G}
        this.addAbility(new ScavengeAbility(new ManaCostsImpl<>("{5}{G}{G}")));
    }

    private BannerhideKrushok(final BannerhideKrushok card) {
        super(card);
    }

    @Override
    public BannerhideKrushok copy() {
        return new BannerhideKrushok(this);
    }
}
