package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PromptoArgentum extends CardImpl {

    public PromptoArgentum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Selfie Shot -- Whenever you cast a noncreature spell, if at least four mana was spent to cast it, create a Treasure token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()),
                StaticFilters.FILTER_NONCREATURE_SPELL_FOUR_MANA_SPENT, false
        ).withFlavorWord("Selfie Shot"));
    }

    private PromptoArgentum(final PromptoArgentum card) {
        super(card);
    }

    @Override
    public PromptoArgentum copy() {
        return new PromptoArgentum(this);
    }
}
