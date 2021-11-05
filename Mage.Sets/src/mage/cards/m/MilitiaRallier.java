package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MilitiaRallier extends CardImpl {

    public MilitiaRallier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Militia Rallier can't attack alone.
        this.addAbility(new CantAttackAloneAbility());

        // Whenever Militia Rallier attacks, untap target creature.
        Ability ability = new AttacksTriggeredAbility(new UntapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MilitiaRallier(final MilitiaRallier card) {
        super(card);
    }

    @Override
    public MilitiaRallier copy() {
        return new MilitiaRallier(this);
    }
}
