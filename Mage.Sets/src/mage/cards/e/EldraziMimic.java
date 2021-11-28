package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class EldraziMimic extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("another colorless creature");

    static {
        FILTER.add(AnotherPredicate.instance);
        FILTER.add(ColorlessPredicate.instance);
    }

    public EldraziMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever another colorless creature enters the battlefield under your control, you may have the base power and toughness of Eldrazi Mimic
        // become that creature's power and toughness until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new EldraziMimicEffect(), FILTER, true, SetTargetPointer.PERMANENT, null));
    }

    private EldraziMimic(final EldraziMimic card) {
        super(card);
    }

    @Override
    public EldraziMimic copy() {
        return new EldraziMimic(this);
    }
}

class EldraziMimicEffect extends OneShotEffect {

    public EldraziMimicEffect() {
        super(Outcome.Detriment);
        staticText = "you may have the base power and toughness of {this} become that creature's power and toughness until end of turn";
    }

    public EldraziMimicEffect(final EldraziMimicEffect effect) {
        super(effect);
    }

    @Override
    public EldraziMimicEffect copy() {
        return new EldraziMimicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            if (permanent != null) {
                ContinuousEffect effect = new SetPowerToughnessTargetEffect(permanent.getPower().getValue(), permanent.getToughness().getValue(), Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}
