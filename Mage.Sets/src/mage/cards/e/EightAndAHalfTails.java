
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author LevelX2
 */
public final class EightAndAHalfTails extends CardImpl {

    public EightAndAHalfTails(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{W}: Target permanent you control gains protection from white until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(ProtectionAbility.from(ObjectColor.WHITE), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{W}"));
        Target target = new TargetControlledPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
        // {1}: Target spell or permanent becomes white until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesColorTargetEffect(ObjectColor.WHITE, Duration.EndOfTurn, "Target spell or permanent becomes white until end of turn"), new ManaCostsImpl<>("{1}"));
        target = new TargetSpellOrPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private EightAndAHalfTails(final EightAndAHalfTails card) {
        super(card);
    }

    @Override
    public EightAndAHalfTails copy() {
        return new EightAndAHalfTails(this);
    }
}
