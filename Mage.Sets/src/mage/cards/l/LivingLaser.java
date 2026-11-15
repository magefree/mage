package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsDiscardedThisTurnCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.DiscardedCardWatcher;

import java.util.UUID;

public final class LivingLaser extends CardImpl {

    public LivingLaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Living Laser attacks, for each card you've discarded this turn, create a token that's a copy of Living Laser, except the token isn't legendary. The tokens enter tapped and attacking. Exile the tokens at the beginning of the next end step.
        Ability ability = new AttacksTriggeredAbility(new LivingLaserEffect());
        ability.addHint(CardsDiscardedThisTurnCount.getHint());
        this.addAbility(ability, new DiscardedCardWatcher());
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
    public LivingLaserEffect copy() {
        return new LivingLaserEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = CardsDiscardedThisTurnCount.SINGULAR.calculate(game, source, this);
        if (amount < 1) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), null, false, amount, true, true
        ).setIsntLegendary(true);
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
        effect.apply(game, source);
        effect.exileTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
