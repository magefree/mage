
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MorphAbility;
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
public final class KolaghanStormsinger extends CardImpl {

    public KolaghanStormsinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Megamorph {R}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{R}"), true));

        // When Kolaghan Stormsinger is turned face up, target creature gains haste until end of turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KolaghanStormsinger(final KolaghanStormsinger card) {
        super(card);
    }

    @Override
    public KolaghanStormsinger copy() {
        return new KolaghanStormsinger(this);
    }
}
