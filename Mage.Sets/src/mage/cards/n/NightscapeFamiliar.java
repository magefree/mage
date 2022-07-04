package mage.cards.n;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author North
 */
public final class NightscapeFamiliar extends CardImpl {

    private static final FilterCard filter = new FilterCard("Blue spells and red spells");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.RED)));
    }

    public NightscapeFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Blue spells and red spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // {1}{B}: Regenerate Nightscape Familiar.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{B}")));
    }

    private NightscapeFamiliar(final NightscapeFamiliar card) {
        super(card);
    }

    @Override
    public NightscapeFamiliar copy() {
        return new NightscapeFamiliar(this);
    }
}
