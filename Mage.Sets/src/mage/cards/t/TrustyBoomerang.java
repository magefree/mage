package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Targets;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrustyBoomerang extends CardImpl {

    public TrustyBoomerang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{1}, {T}: Tap target creature. Return Trusty Boomerang to its owner's hand."
        this.addAbility(new SimpleStaticAbility(new GainAbilityWithAttachmentEffect(
                "equipped creature has \"{1}, {T}: Tap target creature. Return {this} to its owner's hand.\"",
                new Effects(new ReturnToHandTargetEffect(), new TrustyBoomerangEffect()),
                new Targets(new TargetCreaturePermanent()), null,
                new GenericManaCost(1), new TapSourceCost()
        )));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private TrustyBoomerang(final TrustyBoomerang card) {
        super(card);
    }

    @Override
    public TrustyBoomerang copy() {
        return new TrustyBoomerang(this);
    }
}

class TrustyBoomerangEffect extends OneShotEffect {

    TrustyBoomerangEffect() {
        super(Outcome.Benefit);
    }

    private TrustyBoomerangEffect(final TrustyBoomerangEffect effect) {
        super(effect);
    }

    @Override
    public TrustyBoomerangEffect copy() {
        return new TrustyBoomerangEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("attachedPermanent");
        return player != null && permanent != null && player.moveCards(permanent, Zone.HAND, source, game);
    }

    @Override
    public String getText(Mode mode) {
        return "return " +
                Optional.ofNullable((Permanent) getValue("attachedPermanent"))
                        .map(MageObject::getName)
                        .orElse("Trusty Boomerang") +
                " to its owner's hand";
    }
}
