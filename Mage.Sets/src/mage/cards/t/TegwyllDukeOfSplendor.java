package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TegwyllDukeOfSplendor extends CardImpl {

    private static final FilterCreaturePermanent filter =
            new FilterCreaturePermanent(SubType.FAERIE, "Faeries");
    private static final FilterControlledPermanent filter2 =
            new FilterControlledPermanent(SubType.FAERIE, "another Faerie you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    public TegwyllDukeOfSplendor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Other Faeries you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)
        ));

        // Whenever another Faerie you control dies, you draw a card and you lose 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1, "you"),
                false, filter2
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private TegwyllDukeOfSplendor(final TegwyllDukeOfSplendor card) {
        super(card);
    }

    @Override
    public TegwyllDukeOfSplendor copy() {
        return new TegwyllDukeOfSplendor(this);
    }
}
