package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Glimmerbell extends CardImpl {

    public Glimmerbell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.JELLYFISH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{U}: Untap Glimmerbell.
        this.addAbility(new SimpleActivatedAbility(new UntapSourceEffect(), new ManaCostsImpl<>("{1}{U}")));
    }

    private Glimmerbell(final Glimmerbell card) {
        super(card);
    }

    @Override
    public Glimmerbell copy() {
        return new Glimmerbell(this);
    }
}
