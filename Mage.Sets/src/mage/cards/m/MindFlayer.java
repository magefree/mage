package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindFlayer extends CardImpl {

    public MindFlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Dominate Monster — When Mind Flayer enters the battlefield, gain control of target creature for as long as you control Mind Flayer.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MindFlayerEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Dominate Monster"));
    }

    private MindFlayer(final MindFlayer card) {
        super(card);
    }

    @Override
    public MindFlayer copy() {
        return new MindFlayer(this);
    }
}

class MindFlayerEffect extends GainControlTargetEffect {

    MindFlayerEffect() {
        super(Duration.Custom, true);
        staticText = "gain control of target creature for as long as you control {this}";
    }

    private MindFlayerEffect(final MindFlayerEffect effect) {
        super(effect);
    }

    @Override
    public MindFlayerEffect copy() {
        return new MindFlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent == null
                || permanent == null
                || !sourcePermanent.isControlledBy(source.getControllerId())) {
            discard();
            return false;
        }
        return super.apply(game, source);
    }
}
