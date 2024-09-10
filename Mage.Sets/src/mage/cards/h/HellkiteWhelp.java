package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HellkiteWhelp extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature defending player controls");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public HellkiteWhelp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Hellkite Whelp attacks, it deals 1 damage to target creature defending player controls.
        Ability ability = new AttacksTriggeredAbility(
                new DamageTargetEffect(1, "it"), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HellkiteWhelp(final HellkiteWhelp card) {
        super(card);
    }

    @Override
    public HellkiteWhelp copy() {
        return new HellkiteWhelp(this);
    }
}
