package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInASingleGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QutrubForayer extends CardImpl {

    public QutrubForayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, choose one --
        // * Destroy target creature that was dealt damage this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_DAMAGED_THIS_TURN));

        // * Exile up to two target cards from a single graveyard.
        ability.addMode(new Mode(new ExileTargetEffect()).addTarget(new TargetCardInASingleGraveyard(0, 2, StaticFilters.FILTER_CARD_CARDS)));
        this.addAbility(ability);
    }

    private QutrubForayer(final QutrubForayer card) {
        super(card);
    }

    @Override
    public QutrubForayer copy() {
        return new QutrubForayer(this);
    }
}
