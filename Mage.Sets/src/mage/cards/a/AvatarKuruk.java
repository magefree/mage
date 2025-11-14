package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiritWorldToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvatarKuruk extends CardImpl {

    public AvatarKuruk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.nightCard = true;

        // Whenever you cast a spell, create a 1/1 colorless Spirit creature token with "This token can't block or be blocked by non-Spirit creatures."
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new SpiritWorldToken()), StaticFilters.FILTER_SPELL_A, false
        ));

        // Exhaust -- Waterbend {20}: Take an extra turn after this one.
        this.addAbility(new ExhaustAbility(new AddExtraTurnControllerEffect(), new WaterbendCost(20)));
    }

    private AvatarKuruk(final AvatarKuruk card) {
        super(card);
    }

    @Override
    public AvatarKuruk copy() {
        return new AvatarKuruk(this);
    }
}
