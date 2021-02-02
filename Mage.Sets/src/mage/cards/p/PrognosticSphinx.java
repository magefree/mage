
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class PrognosticSphinx extends CardImpl {

    public PrognosticSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Discard a card: Prognostic Sphinx gains hexproof until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.EndOfTurn), new DiscardCardCost());
        Effect effect = new TapSourceEffect();
        effect.setText("Tap it");
        ability.addEffect(effect);
        this.addAbility(ability);
        // Whenever Prognostic Sphinx attacks, scry 3.</i>
        this.addAbility(new AttacksTriggeredAbility(new ScryEffect(3), false));
    }

    private PrognosticSphinx(final PrognosticSphinx card) {
        super(card);
    }

    @Override
    public PrognosticSphinx copy() {
        return new PrognosticSphinx(this);
    }
}
