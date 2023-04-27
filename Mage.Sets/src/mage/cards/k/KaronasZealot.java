
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import static mage.abilities.effects.RedirectionEffect.UsageType.ACCORDING_DURATION;
import mage.abilities.effects.common.RedirectDamageFromSourceToTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class KaronasZealot extends CardImpl {

    public KaronasZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Morph {3}{W}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{W}{W}")));

        // When Karona's Zealot is turned face up, all damage that would be dealt to it this turn is dealt to target creature instead.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new RedirectDamageFromSourceToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE, ACCORDING_DURATION)
                .setText("all damage that would be dealt to it this turn is dealt to target creature instead"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KaronasZealot(final KaronasZealot card) {
        super(card);
    }

    @Override
    public KaronasZealot copy() {
        return new KaronasZealot(this);
    }
}
