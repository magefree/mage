package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.HauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ExhumerThrull extends CardImpl {

    public ExhumerThrull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haunt
        // When Exhumer Thrull enters the battlefield or the creature it haunts dies, return target creature card from your graveyard to your hand.
        Ability ability = new HauntAbility(this, new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private ExhumerThrull(final ExhumerThrull card) {
        super(card);
    }

    @Override
    public ExhumerThrull copy() {
        return new ExhumerThrull(this);
    }
}
