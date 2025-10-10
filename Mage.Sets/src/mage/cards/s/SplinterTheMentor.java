package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PartnerVariantType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.FilterPermanentThisOrAnother;
import mage.filter.StaticFilters;
import mage.game.permanent.token.MutagenToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SplinterTheMentor extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanentThisOrAnother(
            StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, false
    );

    public SplinterTheMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Splinter or another nontoken creature you control leaves the battlefield, create a Mutagen token.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(new CreateTokenEffect(new MutagenToken()), filter));

        // Partner--Character select
        this.addAbility(PartnerVariantType.CHARACTER_SELECT.makeAbility());
    }

    private SplinterTheMentor(final SplinterTheMentor card) {
        super(card);
    }

    @Override
    public SplinterTheMentor copy() {
        return new SplinterTheMentor(this);
    }
}
