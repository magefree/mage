
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MeditateAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Styxo
 */
public final class QuiGonJinn extends CardImpl {

    public QuiGonJinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{U}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.JEDI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Qui-Gon Jinn attacks alone, it gets +2/+2 and lifelink until end of turn.
        Effect effect = new BoostSourceEffect(2, 2, Duration.EndOfTurn);
        effect.setText("it gets +2/+2");
        Ability abitity = new AttacksAloneSourceTriggeredAbility(effect);
        effect = new GainAbilitySourceEffect(LifelinkAbility.getInstance());
        effect.setText("and lifelink until end of turn");
        abitity.addEffect(effect);
        this.addAbility(abitity);

        // When Qui-Gon Jinn leaves the battlefield, you may exile target card from a graveyard.
        abitity = new LeavesBattlefieldTriggeredAbility(new ExileTargetEffect(), true);
        abitity.addTarget(new TargetCardInGraveyard());
        this.addAbility(abitity);

        // Meditate {1}{W}
        this.addAbility(new MeditateAbility(new ManaCostsImpl("{1}{W}")));
    }

    private QuiGonJinn(final QuiGonJinn card) {
        super(card);
    }

    @Override
    public QuiGonJinn copy() {
        return new QuiGonJinn(this);
    }
}
