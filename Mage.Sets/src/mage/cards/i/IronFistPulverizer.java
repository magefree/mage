package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronFistPulverizer extends CardImpl {

    public IronFistPulverizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you cast your second spell each turn, Iron-Fist Pulverizer deals 2 damage to target opponent. Scry 1.
        Ability ability = new CastSecondSpellTriggeredAbility(new DamageTargetEffect(2));
        ability.addEffect(new ScryEffect(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private IronFistPulverizer(final IronFistPulverizer card) {
        super(card);
    }

    @Override
    public IronFistPulverizer copy() {
        return new IronFistPulverizer(this);
    }
}
