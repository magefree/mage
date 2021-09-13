
package mage.cards.k;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author fireshoes
 */
public final class KessigProwler extends CardImpl {

    public KessigProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.WEREWOLF);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.s.SinuousPredator.class;

        // {4}{G}: Transform Kessig Prowler.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), new ManaCostsImpl("{4}{G}")));
    }

    private KessigProwler(final KessigProwler card) {
        super(card);
    }

    @Override
    public KessigProwler copy() {
        return new KessigProwler(this);
    }
}
