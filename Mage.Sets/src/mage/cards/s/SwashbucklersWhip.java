package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SwashbucklersWhip extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public SwashbucklersWhip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has reach, "{2}, {T}: Tap target artifact or creature," and "{8}, {T}: Discover 10."
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ReachAbility.getInstance(), AttachmentType.EQUIPMENT
        ));

        Ability tapAbility = new SimpleActivatedAbility(
                new TapTargetEffect(),
                new GenericManaCost(2)
        );
        tapAbility.addCost(new TapSourceCost());
        tapAbility.addTarget(new TargetPermanent(filter));
        ability.addEffect(new GainAbilityAttachedEffect(tapAbility, AttachmentType.EQUIPMENT)
                .setText(", \"{2}, {T}: Tap target artifact or creature,\""));

        Ability discoverAbility = new SimpleActivatedAbility(
                new DiscoverEffect(10),
                new GenericManaCost(8)
        );
        discoverAbility.addCost(new TapSourceCost());
        ability.addEffect(new GainAbilityAttachedEffect(discoverAbility, AttachmentType.EQUIPMENT)
                .setText(" and \"{8}, {T}: Discover 10.\""));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
    }

    private SwashbucklersWhip(final SwashbucklersWhip card) {
        super(card);
    }

    @Override
    public SwashbucklersWhip copy() {
        return new SwashbucklersWhip(this);
    }
}
