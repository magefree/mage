
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OakheartDryads extends CardImpl {

    public OakheartDryads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.NYMPH);
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Constellation - Whenever Oakheart Dryads or another enchantment enters the battlefield under your control, target creature gets +1/+1 until end of turn.
        Ability ability = new ConstellationAbility(new BoostTargetEffect(1,1, Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private OakheartDryads(final OakheartDryads card) {
        super(card);
    }

    @Override
    public OakheartDryads copy() {
        return new OakheartDryads(this);
    }
}
