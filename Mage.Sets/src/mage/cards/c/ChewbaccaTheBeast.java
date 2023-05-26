package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class ChewbaccaTheBeast extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target attacking creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(AttackingPredicate.instance);
    }

    public ChewbaccaTheBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WOOKIEE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Han Solo, Scrumrat
        this.addAbility(new PartnerWithAbility("Han Solo, Scrumrat"));

        // Whenever Chewbacca, the Beast attacks, another target attacking creature you control gains indestructible until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private ChewbaccaTheBeast(final ChewbaccaTheBeast card) {
        super(card);
    }

    @Override
    public ChewbaccaTheBeast copy() {
        return new ChewbaccaTheBeast(this);
    }
}
