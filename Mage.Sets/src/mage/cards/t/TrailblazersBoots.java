package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author North
 */
public final class TrailblazersBoots extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("nonbasic land");

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    public TrailblazersBoots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has nonbasic landwalk.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new LandwalkAbility(filter), AttachmentType.EQUIPMENT
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private TrailblazersBoots(final TrailblazersBoots card) {
        super(card);
    }

    @Override
    public TrailblazersBoots copy() {
        return new TrailblazersBoots(this);
    }
}
