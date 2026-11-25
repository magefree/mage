package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class TalShiarOperative extends CardImpl {

    public TalShiarOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ROMULAN);
        this.subtype.add(SubType.OFFICER);
        this.subtype.add(SubType.SPY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature attacks, choose one --
        // * Target creature you control gains deathtouch until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(DeathtouchAbility.getInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());

        // * Surveil 1.
        ability.addMode(new Mode(new SurveilEffect(1)));
        this.addAbility(ability);
    }

    private TalShiarOperative(final TalShiarOperative card) {
        super(card);
    }

    @Override
    public TalShiarOperative copy() {
        return new TalShiarOperative(this);
    }
}
