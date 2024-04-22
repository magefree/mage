package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactSpell;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrafnaFounderOfLatNam extends CardImpl {

    private static final FilterSpell filter = new FilterArtifactSpell("artifact spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DrafnaFounderOfLatNam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}{U}: Return target artifact you control to its owner's hand.
        Ability ability = new SimpleActivatedAbility(new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.addAbility(ability);

        // {3}, {T}: Copy target artifact spell you control.
        ability = new SimpleActivatedAbility(new CopyTargetSpellEffect(false, false, false), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private DrafnaFounderOfLatNam(final DrafnaFounderOfLatNam card) {
        super(card);
    }

    @Override
    public DrafnaFounderOfLatNam copy() {
        return new DrafnaFounderOfLatNam(this);
    }
}
