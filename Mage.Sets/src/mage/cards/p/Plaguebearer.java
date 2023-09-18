package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XCMCPermanentAdjuster;

/**
 *
 * @author spjspj
 */
public final class Plaguebearer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonblack creature with mana value X");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public Plaguebearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{X}{B}: Destroy target nonblack creature with converted mana cost X.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{X}{X}{B}"));
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(XCMCPermanentAdjuster.instance);
        this.addAbility(ability);
    }

    private Plaguebearer(final Plaguebearer card) {
        super(card);
    }

    @Override
    public Plaguebearer copy() {
        return new Plaguebearer(this);
    }
}
