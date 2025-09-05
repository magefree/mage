package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class InnerDemonsGangsters extends CardImpl {

    public InnerDemonsGangsters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Discard a card: This creature gets +1/+0 and gains menace until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new BoostSourceEffect(1, 0 , Duration.EndOfTurn).setText("{this} gets +1/+0"),
                new DiscardCardCost()
        );
        ability.addEffect(new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn)
                .setText("and gains menace until end of turn"));
        this.addAbility(ability);
    }

    private InnerDemonsGangsters(final InnerDemonsGangsters card) {
        super(card);
    }

    @Override
    public InnerDemonsGangsters copy() {
        return new InnerDemonsGangsters(this);
    }
}
