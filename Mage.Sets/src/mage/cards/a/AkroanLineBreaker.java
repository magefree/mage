
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class AkroanLineBreaker extends CardImpl {

    public AkroanLineBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Heroic — Whenever you cast a spell that targets Akroan Line Breaker, Akroan Line Breaker gets +2/+0 and gains intimidate until end of turn.
        Effect effect = new BoostSourceEffect(2,0, Duration.EndOfTurn);
        effect.setText("{this} gets +2/+0");
        Ability ability = new HeroicAbility(effect, false);
        effect = new GainAbilitySourceEffect(IntimidateAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains intimidate until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private AkroanLineBreaker(final AkroanLineBreaker card) {
        super(card);
    }

    @Override
    public AkroanLineBreaker copy() {
        return new AkroanLineBreaker(this);
    }
}
