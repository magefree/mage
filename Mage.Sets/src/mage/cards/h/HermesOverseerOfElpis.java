package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BirdVigilanceToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HermesOverseerOfElpis extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.BIRD, "Birds");

    public HermesOverseerOfElpis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast a noncreature spell, create a 1/1 blue Bird creature token with flying and vigilance.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new BirdVigilanceToken()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // Whenever you attack with one or more Birds, scry 2.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new ScryEffect(2), 1, filter));
    }

    private HermesOverseerOfElpis(final HermesOverseerOfElpis card) {
        super(card);
    }

    @Override
    public HermesOverseerOfElpis copy() {
        return new HermesOverseerOfElpis(this);
    }
}
