package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoreDrakkis extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard");

    public LoreDrakkis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Mutate {U/R}{U/R}
        this.addAbility(new MutateAbility(this, "{U/R}{U/R}"));

        // Whenever this creature mutates, return target instant or sorcery card from your graveyard to your hand.
        Ability ability = new MutatesSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private LoreDrakkis(final LoreDrakkis card) {
        super(card);
    }

    @Override
    public LoreDrakkis copy() {
        return new LoreDrakkis(this);
    }
}
