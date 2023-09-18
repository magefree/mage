package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.HeckbentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class DreadWanderer extends CardImpl {

    public DreadWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.JACKAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Dread Wanderer enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {2}{B}: Return Dread Wanderer from your graveyard to the battlefield.
        // Activate this ability only any time you could cast a sorcery and only if you have one or fewer cards in hand.
        ConditionalActivatedAbility ability = new ConditionalActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(),
                new ManaCostsImpl<>("{2}{B}"),
                HeckbentCondition.instance,
                "{2}{B}: Return {this} from your graveyard to the battlefield. "
                + "Activate only as a sorcery "
                + "and only if you have one or fewer cards in hand."
        );
        ability.setTiming(TimingRule.SORCERY);
        addAbility(ability);
    }

    private DreadWanderer(final DreadWanderer card) {
        super(card);
    }

    @Override
    public DreadWanderer copy() {
        return new DreadWanderer(this);
    }
}
