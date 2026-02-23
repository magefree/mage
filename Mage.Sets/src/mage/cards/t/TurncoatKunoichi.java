package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SneakCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurncoatKunoichi extends CardImpl {

    public TurncoatKunoichi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.FOX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Sneak {2}{W}{B}
        this.addAbility(new SneakAbility(this, "{2}{W}{B}"));

        // When this creature enters, choose target creature an opponent controls. Exile that creature until this creature leaves the battlefield. If this creature's sneak cost was paid, instead exile the chosen creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new ExileTargetEffect(), new ExileUntilSourceLeavesEffect(),
                SneakCondition.instance, "choose target creature an opponent controls. " +
                "Exile that creature until this creature leaves the battlefield. " +
                "If this creature's sneak cost was paid, instead exile the chosen creature"
        ));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private TurncoatKunoichi(final TurncoatKunoichi card) {
        super(card);
    }

    @Override
    public TurncoatKunoichi copy() {
        return new TurncoatKunoichi(this);
    }
}
