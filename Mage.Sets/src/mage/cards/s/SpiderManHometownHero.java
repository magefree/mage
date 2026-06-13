package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class SpiderManHometownHero extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public SpiderManHometownHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Spider-Man enters, target creature with power 2 or less can't be blocked this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CantBeBlockedTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SpiderManHometownHero(final SpiderManHometownHero card) {
        super(card);
    }

    @Override
    public SpiderManHometownHero copy() {
        return new SpiderManHometownHero(this);
    }
}
