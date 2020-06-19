package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactOrEnchantmentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrustyRetriever extends CardImpl {

    private static final FilterCard filter
            = new FilterArtifactOrEnchantmentCard("artifact or enchantment card from your graveyard");

    public TrustyRetriever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Trusty Retriever enters the battlefield, choose one —
        // • Put a +1/+1 counter on Trusty Retriever.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));

        // • Return target artifact or enchantment card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private TrustyRetriever(final TrustyRetriever card) {
        super(card);
    }

    @Override
    public TrustyRetriever copy() {
        return new TrustyRetriever(this);
    }
}
