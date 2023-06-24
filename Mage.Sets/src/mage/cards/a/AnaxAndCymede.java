
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HeroicAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AnaxAndCymede extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public AnaxAndCymede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Heroic - Whenever you cast a spell that targets Anax and Cymede, creatures you control get +1/+1 and gain trample until end of turn.
        Effect effect = new BoostControlledEffect(1,1, Duration.EndOfTurn, filter);
        effect.setText("creatures you control get +1/+1");
        Ability ability = new HeroicAbility(effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter);
        effect.setText("and gain trample until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private AnaxAndCymede(final AnaxAndCymede card) {
        super(card);
    }

    @Override
    public AnaxAndCymede copy() {
        return new AnaxAndCymede(this);
    }
}
