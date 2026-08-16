package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author nandmp
 */
public final class HellcatUndyingVigilante extends CardImpl {

    public HellcatUndyingVigilante(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Hellcat dies, return her to the battlefield under her owner's control with a +1/+1 counter on her. She loses all abilities and gains haste.
        this.addAbility(new DiesSourceTriggeredAbility(new HellcatUndyingVigilanteReturnEffect()));
    }

    private HellcatUndyingVigilante(final HellcatUndyingVigilante card) {
        super(card);
    }

    @Override
    public HellcatUndyingVigilante copy() {
        return new HellcatUndyingVigilante(this);
    }
}

class HellcatUndyingVigilanteReturnEffect extends OneShotEffect {

    HellcatUndyingVigilanteReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return her to the battlefield under her owner's control with a +1/+1 counter on her. "
                + "She loses all abilities and gains haste";
    }

    private HellcatUndyingVigilanteReturnEffect(final HellcatUndyingVigilanteReturnEffect effect) {
        super(effect);
    }

    @Override
    public HellcatUndyingVigilanteReturnEffect copy() {
        return new HellcatUndyingVigilanteReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        OneShotEffect effect = new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(
                CounterType.P1P1.createInstance()
        );
        effect.setTargetPointer(new FixedTarget(card, game));
        if (!effect.apply(game, source)) {
            return false;
        }
        game.processAction();
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return false;
        }
        FixedTarget fixedTarget = new FixedTarget(permanent, game);
        game.addEffect(new LoseAllAbilitiesTargetEffect(Duration.Custom)
                .setTargetPointer(fixedTarget), source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom)
                .setTargetPointer(fixedTarget), source);
        return true;
    }
}
