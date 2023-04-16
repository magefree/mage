package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScrappyBruiser extends CardImpl {

    public ScrappyBruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Scrappy Bruiser attacks, up to one target attacking creature gets +2/+0 and gains trample until end of turn. Return that creature to its owner's hand at end of combat.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(2, 0)
                .setText("up to one target attacking creature gets +2/+0"));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample until end of turn"));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(
                new ReturnToHandTargetEffect().setText("Return that creature to its owner's hand at end of combat")
        ).setTriggerPhrase(null), true));
        ability.addTarget(new TargetAttackingCreature(0, 1));
        this.addAbility(ability);
    }

    private ScrappyBruiser(final ScrappyBruiser card) {
        super(card);
    }

    @Override
    public ScrappyBruiser copy() {
        return new ScrappyBruiser(this);
    }
}
