package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DOT7RepairSquad extends CardImpl {

    public DOT7RepairSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{W}: Untap target creature. Activate only as a sorcery and only once each turn.
        ActivatedAbility ability = new LimitedTimesPerTurnActivatedAbility(
            Zone.BATTLEFIELD,
            new UntapTargetEffect(),
            new ManaCostsImpl<>("{1}{W}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTiming(TimingRule.SORCERY);
        this.addAbility(ability);
    }

    private DOT7RepairSquad(final DOT7RepairSquad card) {
        super(card);
    }

    @Override
    public DOT7RepairSquad copy() {
        return new DOT7RepairSquad(this);
    }
}
