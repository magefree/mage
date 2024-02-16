package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SoldierArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThirdPathIconoclast extends CardImpl {

    public ThirdPathIconoclast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a noncreature spell, create a 1/1 colorless Soldier artifact creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new SoldierArtifactToken()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private ThirdPathIconoclast(final ThirdPathIconoclast card) {
        super(card);
    }

    @Override
    public ThirdPathIconoclast copy() {
        return new ThirdPathIconoclast(this);
    }
}
