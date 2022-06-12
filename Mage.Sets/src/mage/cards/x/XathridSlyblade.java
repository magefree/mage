
package mage.cards.x;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class XathridSlyblade extends CardImpl {

    public XathridSlyblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // {3}{B}: Until end of turn, Xathrid Slyblade loses hexproof and gains first strike and deathtouch.
        Effect effect = new LoseAbilitySourceEffect(HexproofAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Until end of turn, {this} loses hexproof");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{3}{B}"));
        Effect effect2 = new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect2.setText("and gains first strike");
        ability.addEffect(effect2);
        Effect effect3 = new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn);
        effect3.setText("and deathtouch");
        ability.addEffect(effect3);
        this.addAbility(ability);
    }

    private XathridSlyblade(final XathridSlyblade card) {
        super(card);
    }

    @Override
    public XathridSlyblade copy() {
        return new XathridSlyblade(this);
    }
}
