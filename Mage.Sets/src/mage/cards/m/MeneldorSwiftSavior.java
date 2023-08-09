package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Susucr
 */
public final class MeneldorSwiftSavior extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public MeneldorSwiftSavior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Meneldor, Swift Savior deals combat damage to a player, exile up to one target creature you own, then return it to the battlefield under your control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ExileThenReturnTargetEffect(true, false), false);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private MeneldorSwiftSavior(final MeneldorSwiftSavior card) {
        super(card);
    }

    @Override
    public MeneldorSwiftSavior copy() {
        return new MeneldorSwiftSavior(this);
    }
}
