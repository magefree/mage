package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.ruleModifying.CantCastDuringYourTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TidalBarracuda extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells");

    public TidalBarracuda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Any player may cast spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(new CastAsThoughItHadFlashAllEffect(
                Duration.WhileOnBattlefield, filter, true
        )));

        // Your opponents can't cast spells during your turn.
        this.addAbility(new SimpleStaticAbility(new CantCastDuringYourTurnEffect()));
    }

    private TidalBarracuda(final TidalBarracuda card) {
        super(card);
    }

    @Override
    public TidalBarracuda copy() {
        return new TidalBarracuda(this);
    }
}
