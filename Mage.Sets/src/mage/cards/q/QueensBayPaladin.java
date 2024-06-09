package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class QueensBayPaladin extends CardImpl {

    private static final FilterCard filter = new FilterCard("Vampire card from your graveyard");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public QueensBayPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever Queen's Bay Paladin enters the battlefield or attacks, return up to one target Vampire card from your graveyard to the battlefield with a finality counter on it. You lose life equal to its mana value.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance()),
                false
        );
        ability.addEffect(
                new LoseLifeSourceControllerEffect(TargetManaValue.instance)
                        .setText("You lose life equal to its mana value")
        );
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter, false));
        this.addAbility(ability);
    }

    private QueensBayPaladin(final QueensBayPaladin card) {
        super(card);
    }

    @Override
    public QueensBayPaladin copy() {
        return new QueensBayPaladin(this);
    }
}