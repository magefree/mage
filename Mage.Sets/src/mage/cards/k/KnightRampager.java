package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.AttackIfAbleTargetRandomOpponentSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.Target;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class KnightRampager extends CardImpl {

    public KnightRampager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Frenzied Rampage — At the beginning of combat on your turn, choose an opponent at random. Knight Rampager attacks that player this combat if able.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new AttackIfAbleTargetRandomOpponentSourceEffect(), TargetController.YOU, false
        ).withFlavorWord("Frenzied Rampage"));

        // When Knight Rampager dies, it deals 4 damage to target opponent chosen at random.
        Ability ability = new DiesSourceTriggeredAbility(
                new DamageTargetEffect(4, "it"), false
        );
        Target target = new TargetOpponent();
        target.setRandom(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private KnightRampager(final KnightRampager card) {
        super(card);
    }

    @Override
    public KnightRampager copy() {
        return new KnightRampager(this);
    }
}
