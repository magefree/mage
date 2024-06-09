package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardTargetControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class ErtaiResurrected extends CardImpl {

    private static final FilterCreatureOrPlaneswalkerPermanent filter =
            new FilterCreatureOrPlaneswalkerPermanent("another target creature or planeswalker");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ErtaiResurrected(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Ertai Resurrected enters the battlefield, choose up to one --
        // * Counter target spell, activated ability, or triggered ability. Its controller draws a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CounterTargetEffect()
                .setText("Counter target spell, activated ability, or triggered ability"));
        ability.addTarget(new TargetStackObject());
        ability.addEffect(new DrawCardTargetControllerEffect(1));
        ability.getModes().setMinModes(0);
        ability.getModes().setMaxModes(1);

        // * Destroy another target creature or planeswalker. Its controller draws a card.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter));
        mode.addEffect(new DrawCardTargetControllerEffect(1));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private ErtaiResurrected(final ErtaiResurrected card) {
        super(card);
    }

    @Override
    public ErtaiResurrected copy() {
        return new ErtaiResurrected(this);
    }
}
