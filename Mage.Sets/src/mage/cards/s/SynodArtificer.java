package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XTargetsAdjuster;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class SynodArtificer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature artifacts");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public SynodArtificer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {X}, {tap}: Tap X target noncreature artifacts.
        Effect tapEffect = new TapTargetEffect();
        tapEffect.setText("Tap X target noncreature artifacts.");
        Ability tapAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, tapEffect, new ManaCostsImpl<>("{X}"));
        tapAbility.addCost(new TapSourceCost());
        tapAbility.setTargetAdjuster(XTargetsAdjuster.instance);
        tapAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(tapAbility);

        // {X}, {tap}: Untap X target noncreature artifacts.
        Effect untapEffect = new UntapTargetEffect();
        untapEffect.setText("Untap X target noncreature artifacts.");
        Ability untapAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, untapEffect, new ManaCostsImpl<>("{X}"));
        untapAbility.addCost(new TapSourceCost());
        untapAbility.setTargetAdjuster(XTargetsAdjuster.instance);
        untapAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(untapAbility);
    }

    private SynodArtificer(final SynodArtificer card) {
        super(card);
    }

    @Override
    public SynodArtificer copy() {
        return new SynodArtificer(this);
    }
}
