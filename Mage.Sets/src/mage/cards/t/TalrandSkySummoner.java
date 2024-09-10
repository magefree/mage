package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.DrakeToken;

import java.util.UUID;

/**
 * @author North
 */
public final class TalrandSkySummoner extends CardImpl {

    public TalrandSkySummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant or sorcery spell, create a 2/2 blue Drake creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new DrakeToken()), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false));
    }

    private TalrandSkySummoner(final TalrandSkySummoner card) {
        super(card);
    }

    @Override
    public TalrandSkySummoner copy() {
        return new TalrandSkySummoner(this);
    }
}
