package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExpendTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrailtrackerScout extends CardImpl {

    public TrailtrackerScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Whenever you expend 8, return target permanent card from your graveyard to your hand.
        Ability ability = new ExpendTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), ExpendTriggeredAbility.Expend.EIGHT
        );
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_PERMANENT));
        this.addAbility(ability);
    }

    private TrailtrackerScout(final TrailtrackerScout card) {
        super(card);
    }

    @Override
    public TrailtrackerScout copy() {
        return new TrailtrackerScout(this);
    }
}
