package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

import java.util.UUID;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class Shadowspear extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Shadowspear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has trample and lifelink.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has trample"));
        ability.addEffect(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and lifelink"));
        this.addAbility(ability);

        // {1}: Permanents your opponents control lose hexproof and indestructible until end of turn.
        ability = new SimpleActivatedAbility(new LoseAbilityAllEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("Permanents your opponents control lose hexproof"), new GenericManaCost(1));
        ability.addEffect(new LoseAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("and indestructible until end of turn"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private Shadowspear(final Shadowspear card) {
        super(card);
    }

    @Override
    public Shadowspear copy() {
        return new Shadowspear(this);
    }
}
