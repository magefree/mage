package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.CohortAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class SpawnbinderMage extends CardImpl {

    public SpawnbinderMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: Tap target creature.
        Ability ability = new CohortAbility(new TapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SpawnbinderMage(final SpawnbinderMage card) {
        super(card);
    }

    @Override
    public SpawnbinderMage copy() {
        return new SpawnbinderMage(this);
    }
}
