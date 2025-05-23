package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShamblingCieth extends CardImpl {

    public ShamblingCieth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This creature enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Whenever you cast a noncreature spell, you may pay {B}. If you do, return this card from your graveyard to your hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{B}")),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false, SetTargetPointer.NONE
        ));
    }

    private ShamblingCieth(final ShamblingCieth card) {
        super(card);
    }

    @Override
    public ShamblingCieth copy() {
        return new ShamblingCieth(this);
    }
}
