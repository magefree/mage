
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.dynamicvalue.common.AuraAttachedCount;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Elemental31TrampleHasteToken;

/**
 *
 * @author spjspj
 */
public final class ValdukKeeperOfTheFlame extends CardImpl {

    public ValdukKeeperOfTheFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, for each Aura and Equipment attached to Valduk, Keeper of the Flame, create a 3/1 red Elemental creature token with trample and haste. Exile those tokens at the beginning of the next end step.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new ValdukKeeperOfTheFlameEffect(), TargetController.YOU, false));
    }

    private ValdukKeeperOfTheFlame(final ValdukKeeperOfTheFlame card) {
        super(card);
    }

    @Override
    public ValdukKeeperOfTheFlame copy() {
        return new ValdukKeeperOfTheFlame(this);
    }
}

class ValdukKeeperOfTheFlameEffect extends OneShotEffect {

    public ValdukKeeperOfTheFlameEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "for each Aura and Equipment attached to {this}, create a 3/1 red Elemental creature token with trample and haste. Exile those tokens at the beginning of the next end step";
    }

    public ValdukKeeperOfTheFlameEffect(final ValdukKeeperOfTheFlameEffect effect) {
        super(effect);
    }

    @Override
    public ValdukKeeperOfTheFlameEffect copy() {
        return new ValdukKeeperOfTheFlameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            EquipmentAttachedCount eamount = new EquipmentAttachedCount();
            int value = eamount.calculate(game, source, this);
            AuraAttachedCount aamount = new AuraAttachedCount();
            value += aamount.calculate(game, source, this);
            CreateTokenEffect effect = new CreateTokenEffect(new Elemental31TrampleHasteToken(), value);
            if (effect.apply(game, source)) {
                effect.exileTokensCreatedAtNextEndStep(game, source);
                return true;
            }
        }
        return false;
    }
}
