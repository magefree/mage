
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class Woodcloaker extends CardImpl {

    public Woodcloaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Morph {2}{G}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{G}{G}")));
        // When Woodcloaker is turned face up, target creature gains trample until end of turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Woodcloaker(final Woodcloaker card) {
        super(card);
    }

    @Override
    public Woodcloaker copy() {
        return new Woodcloaker(this);
    }
}
