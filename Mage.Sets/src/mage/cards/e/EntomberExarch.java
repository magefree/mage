package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class EntomberExarch extends CardImpl {

    public EntomberExarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Entomber Exarch enters the battlefield, choose one —
        // • Return target creature card from your graveyard to your hand
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // • Target opponent reveals their hand. You choose a noncreature card from it. That player discards that card.
        Mode mode = new Mode();
        mode.addEffect(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_CREATURE));
        mode.addTarget(new TargetOpponent());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private EntomberExarch(final EntomberExarch card) {
        super(card);
    }

    @Override
    public EntomberExarch copy() {
        return new EntomberExarch(this);
    }
}
