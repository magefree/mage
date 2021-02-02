
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class WarpedResearcher extends CardImpl {

    public WarpedResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever a player cycles a card, Warped Researcher gains flying and shroud until end of turn.
        Effect effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("{this} gains flying");
        Ability ability = new CycleAllTriggeredAbility(effect, false);
        effect = new GainAbilitySourceEffect(ShroudAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and shroud until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private WarpedResearcher(final WarpedResearcher card) {
        super(card);
    }

    @Override
    public WarpedResearcher copy() {
        return new WarpedResearcher(this);
    }
}
