package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.condition.common.SourceOnBattlefieldControlUnchangedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.LostControlWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class RoilElemental extends CardImpl {

    public RoilElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        String rule = "you may gain control of target creature for as long as you control Roil Elemental";

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Landfall - Whenever a land enters the battlefield under your control, you may gain control of target creature for as long as you control Roil Elemental.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new GainControlTargetEffect(Duration.Custom), new SourceOnBattlefieldControlUnchangedCondition(), rule);
        Ability ability = new LandfallAbility(Zone.BATTLEFIELD, effect, true);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addWatcher(new LostControlWatcher());
        this.addAbility(ability);
    }

    private RoilElemental(final RoilElemental card) {
        super(card);
    }

    @Override
    public RoilElemental copy() {
        return new RoilElemental(this);
    }
}
