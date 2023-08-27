package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BirdToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightOfDoves extends CardImpl {

    public KnightOfDoves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever an enchantment you control is put into a graveyard from the battlefield, create a 1/1 white Bird creature token with flying.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new BirdToken()), false,
                StaticFilters.FILTER_CONTROLLED_PERMANENT_AN_ENCHANTMENT, false
        ));
    }

    private KnightOfDoves(final KnightOfDoves card) {
        super(card);
    }

    @Override
    public KnightOfDoves copy() {
        return new KnightOfDoves(this);
    }
}
