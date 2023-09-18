package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class YoreTillerNephilim extends CardImpl {

    public YoreTillerNephilim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}");
        this.subtype.add(SubType.NEPHILIM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Yore-Tiller Nephilim attacks, return target creature card from your graveyard to the battlefield tapped and attacking.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(true, true), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private YoreTillerNephilim(final YoreTillerNephilim card) {
        super(card);
    }

    @Override
    public YoreTillerNephilim copy() {
        return new YoreTillerNephilim(this);
    }
}
