
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BloodrushAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class GhorClanRampager extends CardImpl {

    public GhorClanRampager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Bloodrush - {R}{G}, Discard Ghor-Clan Rampager: Target attacking creature gets +4/+4 and gains trample until end of turn.
        Ability ability = new BloodrushAbility("{R}{G}",new BoostTargetEffect(4,4, Duration.EndOfTurn)
                .setText("target attacking creature gets +4/+4"));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains trample until end of turn"));
        this.addAbility(ability);
    }

    private GhorClanRampager(final GhorClanRampager card) {
        super(card);
    }

    @Override
    public GhorClanRampager copy() {
        return new GhorClanRampager(this);
    }
}
