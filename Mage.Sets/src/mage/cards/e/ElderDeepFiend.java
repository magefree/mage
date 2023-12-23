package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class ElderDeepFiend extends CardImpl {

    public ElderDeepFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.OCTOPUS);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Emerge {5}{U}{U}
        this.addAbility(new EmergeAbility(this, "{5}{U}{U}"));

        // When you cast Elder Deep-Fiend, tap up to four target permanents.
        Ability ability = new CastSourceTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetPermanent(0, 4, StaticFilters.FILTER_PERMANENTS, false));
        this.addAbility(ability);
    }

    private ElderDeepFiend(final ElderDeepFiend card) {
        super(card);
    }

    @Override
    public ElderDeepFiend copy() {
        return new ElderDeepFiend(this);
    }
}
