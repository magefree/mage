package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.RakdosGuildmageGoblinToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ChiefMagistrateOfMercadia extends CardImpl {

    public ChiefMagistrateOfMercadia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Chief Magistrate of Mercadia enters, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of your upkeep, create a 2/1 red Goblin creature token with haste. Then if you're the monarch, for each creature token you control, create a token that's a copy of it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new RakdosGuildmageGoblinToken()));
        ability.addEffect(new ConditionalOneShotEffect(
            new ChiefMagistrateOfMercadiaEffect(),
            MonarchIsSourceControllerCondition.instance
        ).concatBy("Then"));
        this.addAbility(ability);
    }

    private ChiefMagistrateOfMercadia(final ChiefMagistrateOfMercadia card) {
        super(card);
    }

    @Override
    public ChiefMagistrateOfMercadia copy() {
        return new ChiefMagistrateOfMercadia(this);
    }
}

class ChiefMagistrateOfMercadiaEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    static { filter.add(TokenPredicate.TRUE); }

    ChiefMagistrateOfMercadiaEffect() {
        super(Outcome.Benefit);
        staticText = "for each creature token you control, create a token that's a copy of it";
    }

    private ChiefMagistrateOfMercadiaEffect(final ChiefMagistrateOfMercadiaEffect effect) {
        super(effect);
    }

    @Override
    public ChiefMagistrateOfMercadiaEffect copy() {
        return new ChiefMagistrateOfMercadiaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (permanent.isControlledBy(source.getControllerId())) {
                    CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
