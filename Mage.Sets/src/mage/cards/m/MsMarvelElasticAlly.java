package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerGreaterThanBasePowerPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class MsMarvelElasticAlly extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(PowerGreaterThanBasePowerPredicate.instance);
    }

    public MsMarvelElasticAlly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.INHUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Ms. Marvel enters, target creature gets +2/+0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 0));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever a creature you control with power greater than its base power deals combat damage to a player, draw a card. This ability triggers only once each turn.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
            new DrawCardSourceControllerEffect(1),
            filter,
            false, SetTargetPointer.NONE, true
        ).setTriggersLimitEachTurn(1));
    }

    private MsMarvelElasticAlly(final MsMarvelElasticAlly card) {
        super(card);
    }

    @Override
    public MsMarvelElasticAlly copy() {
        return new MsMarvelElasticAlly(this);
    }
}
