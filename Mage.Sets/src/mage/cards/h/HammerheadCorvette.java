package mage.cards.h;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author NinthWorld
 */
public final class HammerheadCorvette extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Starship creature defending player controls");

    static {
        filter.add(SubType.STARSHIP.getPredicate());
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public HammerheadCorvette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Whenever Hammerhead Corvette attacks, you may untap target Starship creature defending player controls and have that creature block Hammerhead Corvette this turn if able.
        Effect effect1 = new UntapTargetEffect();
        Effect effect2 = new MustBeBlockedByTargetSourceEffect(Duration.EndOfTurn);
        Ability ability = new AttacksTriggeredAbility(effect1, true,
                "Whenever {this} attacks, you may uptap target Starship creature defending player controls and have that creature block {this} this turn if able");
        ability.addEffect(effect2);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private HammerheadCorvette(final HammerheadCorvette card) {
        super(card);
    }

    @Override
    public HammerheadCorvette copy() {
        return new HammerheadCorvette(this);
    }
}
