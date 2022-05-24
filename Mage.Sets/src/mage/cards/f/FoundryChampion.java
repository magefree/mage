package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class FoundryChampion extends CardImpl {

    public FoundryChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //When Foundry Champion enters the battlefield, it deals damage to any target equal to the number of creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(CreaturesYouControlCount.instance, "it"));
        ability.addTarget(new TargetAnyTarget());
        ability.addHint(CreaturesYouControlHint.instance);
        this.addAbility(ability);

        //{R}: Foundry Champion gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));

        //{W}: Foundry Champion gets +0/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(0, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{W}")));
    }

    private FoundryChampion(final FoundryChampion card) {
        super(card);
    }

    @Override
    public FoundryChampion copy() {
        return new FoundryChampion(this);
    }
}
