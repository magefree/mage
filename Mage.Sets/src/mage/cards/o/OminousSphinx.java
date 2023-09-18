
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class OminousSphinx extends CardImpl {

    public OminousSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cycle or discard a card,target creature an opponent controls gets -2/-0 until end of turn.
        CycleOrDiscardControllerTriggeredAbility ability = new CycleOrDiscardControllerTriggeredAbility(new BoostTargetEffect(-2, -0, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private OminousSphinx(final OminousSphinx card) {
        super(card);
    }

    @Override
    public OminousSphinx copy() {
        return new OminousSphinx(this);
    }
}
