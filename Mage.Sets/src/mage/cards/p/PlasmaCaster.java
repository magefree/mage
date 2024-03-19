package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.FlipCoinEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.AttachmentType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author justinjohnson14
 */
public final class PlasmaCaster extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that's blocking it");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public PlasmaCaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.

        // Whenever equipped creature attacks, you get {E}{E}.
        this.addAbility(new AttacksTriggeredAbility(new GetEnergyCountersControllerEffect(2), false));

        // Pay {E}{E}: Choose target creature that's blocking equipped creature. Flip a coin. If you win the flip, exile the chosen creature. Otherwise, Plasma Caster deals 1 damage to it.
        Ability ability = new SimpleActivatedAbility(
                new FlipCoinEffect(new ExileTargetEffect(),new DamageTargetEffect(1, "this creature")), new PayEnergyCost(2)
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private PlasmaCaster(final PlasmaCaster card) {
        super(card);
    }

    @Override
    public PlasmaCaster copy() {
        return new PlasmaCaster(this);
    }
}
