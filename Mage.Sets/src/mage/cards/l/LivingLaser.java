package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.watchers.common.DiscardedCardWatcher;

import java.util.UUID;

public final class LivingLaser extends CardImpl {

    public LivingLaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL, SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(mage.abilities.keyword.HasteAbility.getInstance());

        Ability ability = new AttacksTriggeredAbility(new LivingLaserEffect());
        ability.addWatcher(new DiscardedCardWatcher());
        this.addAbility(ability);
    }

    private LivingLaser(final LivingLaser card) {
        super(card);
    }

    @Override
    public LivingLaser copy() {
        return new LivingLaser(this);
    }
}

class LivingLaserEffect extends OneShotEffect {

    LivingLaserEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "for each card you've discarded this turn, create a token that's a copy of {this}, "
                + "except the token isn't legendary. The tokens enter tapped and attacking. "
                + "Exile the tokens at the beginning of the next end step";
    }

    private LivingLaserEffect(final LivingLaserEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int number = DiscardedCardWatcher.getDiscarded(source.getControllerId(), game);
        if (number == 0) {
            return true;
        }

        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, null, false, number, true, true
        ).setIsntLegendary(true);
        effect.setTargetPointer(new mage.target.targetpointer.FixedTarget(source.getSourceId(), game));
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
        }
        return true;
    }

    @Override
    public LivingLaserEffect copy() {
        return new LivingLaserEffect(this);
    }
}
