package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SurgedCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TyrantOfValakut extends CardImpl {

    public TyrantOfValakut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Surge {3}{R}{R} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        addAbility(new SurgeAbility(this, "{3}{R}{R}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Tyrant of Valakut enters the battlefield, if its surge cost was paid, it deals 3 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3))
                .withInterveningIf(SurgedCondition.instance).withRuleTextReplacement(true);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private TyrantOfValakut(final TyrantOfValakut card) {
        super(card);
    }

    @Override
    public TyrantOfValakut copy() {
        return new TyrantOfValakut(this);
    }
}
