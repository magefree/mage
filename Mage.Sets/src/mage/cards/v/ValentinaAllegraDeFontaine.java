package mage.cards.v;

import java.util.Arrays;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ValentinaAllegraDeFontaine extends CardImpl {

    private static final FilterCreaturePermanent other_villains = new FilterCreaturePermanent("Other Villains you control");
    private static final FilterPermanent hero = new FilterControlledPermanent(SubType.HERO);

    static {
        other_villains.add(AnotherPredicate.instance);
        other_villains.add(SubType.VILLAIN.getPredicate());
        other_villains.add(TargetController.YOU.getControllerPredicate());
    }

    public ValentinaAllegraDeFontaine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other Villains you control are Heroes in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new BecomesSubtypeAllEffect(
            Duration.WhileOnBattlefield, Arrays.asList(SubType.HERO), other_villains, false
        ).setText("Other Villains you control are Heroes in addition to their other types.")));

        // {T}: Target Hero you control connives. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new ConniveTargetEffect(), new TapSourceCost()
        );
        ability.addTarget(new TargetPermanent(hero));
        this.addAbility(ability);
    }

    private ValentinaAllegraDeFontaine(final ValentinaAllegraDeFontaine card) {
        super(card);
    }

    @Override
    public ValentinaAllegraDeFontaine copy() {
        return new ValentinaAllegraDeFontaine(this);
    }
}
