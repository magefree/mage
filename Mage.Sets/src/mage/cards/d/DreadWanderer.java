package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.HeckbentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;

import java.util.UUID;

/**
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
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(),
                new ManaCostsImpl<>("{2}{B}"), HeckbentCondition.instance
        ).setTiming(TimingRule.SORCERY));
    }

    private DreadWanderer(final DreadWanderer card) {
        super(card);
    }

    @Override
    public DreadWanderer copy() {
        return new DreadWanderer(this);
    }
}
