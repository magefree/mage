package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class AliBaba extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.WALL, "Wall");

    public AliBaba(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}: Tap target Wall.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AliBaba(final AliBaba card) {
        super(card);
    }

    @Override
    public AliBaba copy() {
        return new AliBaba(this);
    }
}
