
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.AssistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author TheElk801
 */
public final class FanFavorite extends CardImpl {

    public FanFavorite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Assist
        this.addAbility(new AssistAbility());

        // {2}: Fan Favorite gets +1/+1 until end of turn. Any player may activate this ability.
        ActivatedAbility ability = new SimpleActivatedAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(2));
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private FanFavorite(final FanFavorite card) {
        super(card);
    }

    @Override
    public FanFavorite copy() {
        return new FanFavorite(this);
    }
}
