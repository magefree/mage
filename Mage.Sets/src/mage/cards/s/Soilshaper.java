

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetLandPermanent;

/**
 * @author Loki
 */
public final class Soilshaper extends CardImpl {


    public Soilshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a Spirit or Arcane spell, target land becomes a 3/3 creature until end of turn. It's still a land.
        Ability ability = new SpellCastControllerTriggeredAbility(new BecomesCreatureTargetEffect(new CreatureToken(3, 3), false, true, Duration.EndOfTurn), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private Soilshaper(final Soilshaper card) {
        super(card);
    }

    @Override
    public Soilshaper copy() {
        return new Soilshaper(this);
    }

}