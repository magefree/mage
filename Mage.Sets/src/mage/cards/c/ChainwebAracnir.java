package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.EscapesWithAbility;
import mage.abilities.effects.common.DamageWithPowerFromSourceToAnotherTargetEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChainwebAracnir extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creature with flying an opponent controls");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public ChainwebAracnir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Chainweb Aracnir enters the battlefield, it deals damage equal to its power to target creature with flying an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageWithPowerFromSourceToAnotherTargetEffect("it"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Escape—{3}{G}{G}, Exile four other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{3}{G}{G}", 4));

        // CHainweb Aracnir escapes with three +1/+1 counters on it.
        this.addAbility(new EscapesWithAbility(3));
    }

    private ChainwebAracnir(final ChainwebAracnir card) {
        super(card);
    }

    @Override
    public ChainwebAracnir copy() {
        return new ChainwebAracnir(this);
    }
}
