package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class LaccolithWhelp extends CardImpl {

    public LaccolithWhelp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Laccolith Whelp becomes blocked, you may have it deal damage equal to its power to target creature. If you do, Laccolith Grunt assigns no combat damage this turn.
        Ability ability = new BecomesBlockedSourceTriggeredAbility(new DamageTargetEffect(new SourcePermanentPowerCount()).setText("it deal damage equal to its power to target creature"), true);
        ability.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn, true));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private LaccolithWhelp(final LaccolithWhelp card) {
        super(card);
    }

    @Override
    public LaccolithWhelp copy() {
        return new LaccolithWhelp(this);
    }
}
