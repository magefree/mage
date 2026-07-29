package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class PhotonBlastBarrage extends CardImpl {

    public PhotonBlastBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // When you cast this spell, copy it X times. You may choose new targets for the copies.
        this.addAbility(new CastSourceTriggeredAbility(new PhotonBlastBarrageCopyEffect()));

        // Photon Blast Barrage deals 1 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PhotonBlastBarrage(final PhotonBlastBarrage card) {
        super(card);
    }

    @Override
    public PhotonBlastBarrage copy() {
        return new PhotonBlastBarrage(this);
    }
}

class PhotonBlastBarrageCopyEffect extends OneShotEffect {

    PhotonBlastBarrageCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "copy it X times. You may choose new targets for the copies";
    }

    private PhotonBlastBarrageCopyEffect(final PhotonBlastBarrageCopyEffect effect) {
        super(effect);
    }

    @Override
    public PhotonBlastBarrageCopyEffect copy() {
        return new PhotonBlastBarrageCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        int amount = GetXValue.instance.calculate(game, source, this);
        if (spell == null || amount < 1) {
            return false;
        }
        spell.createCopyOnStack(game, source, source.getControllerId(), true, amount);
        return true;
    }
}
