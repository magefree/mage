package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
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
public final class ZealousLorecaster extends CardImpl {

    public ZealousLorecaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When this creature enters, return target instant or sorcery card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        this.addAbility(ability);
    }

    private ZealousLorecaster(final ZealousLorecaster card) {
        super(card);
    }

    @Override
    public ZealousLorecaster copy() {
        return new ZealousLorecaster(this);
    }
}
