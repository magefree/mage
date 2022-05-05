package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PhyrexianGermToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class GripOfPhyresis extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Equipment");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public GripOfPhyresis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Gain control of target Equipment, then create a 0/0 black Germ creature token and attach that Equipment to it.
        GainControlTargetEffect effect = new GainControlTargetEffect(Duration.EndOfGame, true);
        effect.setText("Gain control of target equipment");
        this.getSpellAbility().addEffect(effect);
        Target targetEquipment = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(targetEquipment);
        this.getSpellAbility().addEffect(new GripOfPhyresisEffect());
    }

    private GripOfPhyresis(final GripOfPhyresis card) {
        super(card);
    }

    @Override
    public GripOfPhyresis copy() {
        return new GripOfPhyresis(this);
    }
}

class GripOfPhyresisEffect extends CreateTokenEffect {

    GripOfPhyresisEffect() {
        super(new PhyrexianGermToken());
    }

    GripOfPhyresisEffect(final GripOfPhyresisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent equipment = game.getPermanent(source.getFirstTarget());

        if (controller != null && equipment != null) {
            if (super.apply(game, source)) {
                Permanent germ = game.getPermanent(this.getLastAddedTokenIds().stream().findFirst().orElse(null));
                if (germ != null) {
                    germ.addAttachment(equipment.getId(), source, game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GripOfPhyresisEffect copy() {
        return new GripOfPhyresisEffect(this);
    }
}
