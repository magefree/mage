package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProteanThaumaturge extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ProteanThaumaturge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Constellation — Whenever an enchantment enters the battlefield under your control, you may have Protean Thaumaturge become a copy of another target creature, except it has this ability.
        this.addAbility(createAbility());
    }

    private ProteanThaumaturge(final ProteanThaumaturge card) {
        super(card);
    }

    @Override
    public ProteanThaumaturge copy() {
        return new ProteanThaumaturge(this);
    }

    static Ability createAbility() {
        Ability ability = new ConstellationAbility(new CopyPermanentEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                new ProteanThaumaturgeCopyApplier(), true
        ).setText("{this} become a copy of another target creature, except it has this ability"), true, false);
        ability.addTarget(new TargetPermanent(filter));
        return ability;
    }
}

class ProteanThaumaturgeCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.getAbilities().add(ProteanThaumaturge.createAbility());
        return true;
    }
}
