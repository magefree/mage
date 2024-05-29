
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.InsectToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SpringheartNantuko extends CardImpl {

    public SpringheartNantuko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {1}{G}
        this.addAbility(new BestowAbility(this, "{1}{G}"));

        // Enchanted creature gets +1/+1
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(1, 1)));

        // Landfall — Whenever a land enters the battlefield under your control, you may pay {1}{G} if Springheart Nantuko is attached to a creature you control. If you do, create a token that's a copy of that creature. If you didn't create a token this way, create a 1/1 green Insect creature token.
        this.addAbility(new LandfallAbility(new SpringheartNantukoEffect()));
    }

    private SpringheartNantuko(final SpringheartNantuko card) {
        super(card);
    }

    @Override
    public SpringheartNantuko copy() {
        return new SpringheartNantuko(this);
    }
}

class SpringheartNantukoEffect extends OneShotEffect {

    SpringheartNantukoEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you may pay {1}{G} if {this} is attached to a creature you control. If you do, create a token that's a copy of that creature. If you didn't create a token this way, create a 1/1 green Insect creature token";
    }

    private SpringheartNantukoEffect(final SpringheartNantukoEffect effect) {
        super(effect);
    }

    @Override
    public SpringheartNantukoEffect copy() {
        return new SpringheartNantukoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent nantuko = game.getPermanent(source.getSourceId()); // intentionally no LKI
        if (nantuko != null) {
            Permanent attachedTo = game.getPermanent(nantuko.getAttachedTo()); // intentionally no LKI
            if (StaticFilters.FILTER_CONTROLLED_CREATURE.match(attachedTo, controller.getId(), source, game)) {
                // you may pay {1}{G} if Springheart Nantuko is attached to a creature you control,
                Cost cost = new ManaCostsImpl<>("{1}{G}");
                if (cost.canPay(source, source, controller.getId(), game)
                        && controller.chooseUse(Outcome.PutCreatureInPlay, "Pay {1}{G} to make a copy of " + attachedTo.getIdName() + "?", source, game)
                        && cost.pay(source, game, source, controller.getId(), false, null)) {
                    // If you do, create a token that's a copy of that creature.
                    if (new CreateTokenCopyTargetEffect()
                            .setTargetPointer(new FixedTarget(attachedTo, game))
                            .apply(game, source)) {
                        return true;
                    }
                }
            }
        }
        // If you didn't create a token this way, create a 1/1 green Insect creature token
        // Note: current understanding of the ability is that if you can't (notably if the condition is not met for whatever reason) or don't pay, you create a 1/1 Insect
        new CreateTokenEffect(new InsectToken()).apply(game, source);
        return true;
    }

}