package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToYouAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class MikaeusTheUnhallowed extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Human creatures");
    private static final FilterPermanent filterHuman = new FilterPermanent(SubType.HUMAN, "Human");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public MikaeusTheUnhallowed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(IntimidateAbility.getInstance());

        // Whenever a Human deals damage to you, destroy it.
        this.addAbility(new DealsDamageToYouAllTriggeredAbility(filterHuman,
                new DestroyTargetEffect().setText("destroy it"), false));

        // Other non-Human creatures you control get +1/+1 and have undying.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true));
        ability.addEffect(new GainAbilityControlledEffect(new UndyingAbility(), Duration.WhileOnBattlefield, filter, true)
                .setText("and have undying. <i>(When a creature with undying dies, if it had no +1/+1 counters on it," +
                        " return it to the battlefield under its owner's control with a +1/+1 counter on it.)</i>"));
        this.addAbility(ability);
    }

    private MikaeusTheUnhallowed(final MikaeusTheUnhallowed card) {
        super(card);
    }

    @Override
    public MikaeusTheUnhallowed copy() {
        return new MikaeusTheUnhallowed(this);
    }
}
