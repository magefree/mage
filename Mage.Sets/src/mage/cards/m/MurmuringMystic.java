package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BirdIllusionToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MurmuringMystic extends CardImpl {

    public MurmuringMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Whenever you cast an instant or sorcery spell, create a 1/1 blue Bird Illusion creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new BirdIllusionToken()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private MurmuringMystic(final MurmuringMystic card) {
        super(card);
    }

    @Override
    public MurmuringMystic copy() {
        return new MurmuringMystic(this);
    }
}
