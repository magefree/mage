package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.ComparisonType;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author jeffwadsworth
 */
public final class PentarchPaladin extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent of the chosen color.");

    public PentarchPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flanking
        this.addAbility(new FlankingAbility());

        // As Pentarch Paladin enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Detriment)));

        // {W}{W}, {tap}: Destroy target permanent of the chosen color.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{W}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(PentarchPaladinAdjuster.instance);
        this.addAbility(ability);
    }

    private PentarchPaladin(final PentarchPaladin card) {
        super(card);
    }

    @Override
    public PentarchPaladin copy() {
        return new PentarchPaladin(this);
    }
}

enum PentarchPaladinAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ObjectColor chosenColor = (ObjectColor) game.getState().getValue(ability.getSourceId() + "_color");
        ability.getTargets().clear();
        FilterPermanent filter = new FilterPermanent("permanent of the chosen color.");
        if (chosenColor != null) {
            filter.add(new ColorPredicate(chosenColor));
        } else {
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, -5));// Pretty sure this is always false
        }
        TargetPermanent oldTargetPermanent = new TargetPermanent(filter);
        ability.addTarget(oldTargetPermanent);
    }
}
